package com.example.domain

import com.example.domain.adaptation.PromptBuilder
import com.example.llm.LlmClient
import com.example.persistence.InMemoryConversationStore
import com.example.persistence.InMemoryUserProfileStore
import com.example.study.InMemoryStudyDataStore
import com.example.study.StudyMessageRecord

class ChatService(
    private val store: InMemoryConversationStore,
    private val profileStore: InMemoryUserProfileStore,
    private val llmClient: LlmClient,
    private val studyDataStore: InMemoryStudyDataStore
) {
    companion object {
        private const val MIN_USER_MESSAGES = 3
        private const val MAX_USER_MESSAGES = 10
    }

    suspend fun startConversation(
        conversationId: String,
        userId: String,
        topic: String,
        mode: String
    ): String {
        val existingHistory = store.get(conversationId)
        if (existingHistory.isNotEmpty()) {
            val firstAssistantMessage = existingHistory.firstOrNull { it.role == Role.ASSISTANT }
            return firstAssistantMessage?.content ?: ""
        }

        val profile = profileStore.get(conversationId)
        val systemPrompt = PromptBuilder.buildSystemPrompt(profile, mode)

        val openingInstruction = """
        Start the conversation with the participant about this topic:
        "$topic"

        Write only the first assistant message.

        Requirements:
        - Follow the communication style defined in the system prompt.
        - Keep it natural and suitable for a short study conversation.
        - Do not mention the study, the prompt, the mode, or the user's preferences.
        - Ask exactly one simple opening question.
        """.trimIndent()

        val openingMessage = llmClient.complete(
            systemPrompt,
            listOf(Message(Role.USER, openingInstruction))
        )


        store.append(conversationId, Message(Role.ASSISTANT, openingMessage))

        studyDataStore.addMessage(
            StudyMessageRecord(
                userId = userId,
                conversationId = conversationId,
                topic = topic,
                mode = mode,
                role = "ASSISTANT",
                content = openingMessage
            )
        )

        return openingMessage
    }


    suspend fun reply(
        conversationId: String,
        userId: String,
        userMessage: String,
        topic: String,
        mode: String
    ): String {
        val currentUserMessageCount = store.get(conversationId)
            .count { it.role == Role.USER }

        if (currentUserMessageCount >= MAX_USER_MESSAGES) {
            return "This round is complete. Please continue to the next step."
        }

        store.append(conversationId, Message(Role.USER, userMessage))

        studyDataStore.addMessage(
            StudyMessageRecord(
                userId = userId,
                conversationId = conversationId,
                topic = topic,
                mode = mode,
                role = "USER",
                content = userMessage
            )
        )

        val updatedUserMessageCount = currentUserMessageCount + 1
        val isFinalUserMessage = updatedUserMessageCount >= MAX_USER_MESSAGES

        val profile = profileStore.get(conversationId)
        val systemPrompt = PromptBuilder.buildSystemPrompt(profile, mode)


        val history = if (isFinalUserMessage) {
            store.get(conversationId).takeLast(20) + Message(
                Role.USER,
                """
                This is the participant's final message in this round.
                Respond naturally and briefly.
                End the conversation in a friendly way.
                Do not ask another question.
            """.trimIndent()
            )
        } else {
            store.get(conversationId).takeLast(20)
        }

        val finalReply = llmClient.complete(systemPrompt, history)

        store.append(conversationId, Message(Role.ASSISTANT, finalReply))

        studyDataStore.addMessage(
            StudyMessageRecord(
                userId = userId,
                conversationId = conversationId,
                topic = topic,
                mode = mode,
                role = "ASSISTANT",
                content = finalReply
            )
        )

        return finalReply
    }

}

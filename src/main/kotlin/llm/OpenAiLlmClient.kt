package com.example.llm

import com.example.domain.Message
import com.example.domain.Role
import com.openai.client.OpenAIClient
import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.chat.completions.ChatCompletionCreateParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenAiLlmClient(
    private val model: ChatModel = ChatModel.GPT_4_1_MINI
) : LlmClient {

    private val client: OpenAIClient = OpenAIOkHttpClient.fromEnv()

    override suspend fun complete(systemPrompt: String, history: List<Message>): String =
        withContext(Dispatchers.IO) {
            val builder = ChatCompletionCreateParams.builder()
                .model(model)
                .addSystemMessage(systemPrompt)

            history.forEach { message ->
                when (message.role) {
                    Role.USER -> builder.addUserMessage(message.content)
                    Role.ASSISTANT -> builder.addAssistantMessage(message.content)
                }
            }

            val completion = client.chat().completions().create(builder.build())

            val firstChoice = completion.choices().firstOrNull()
            val contentOptional = firstChoice?.message()?.content()

            contentOptional?.orElse("Sorry, I could not generate a response.")
                ?: "Sorry, I could not generate a response."
        }
}


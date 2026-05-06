package com.example.persistence

import com.example.domain.Message
import java.util.concurrent.ConcurrentHashMap

class InMemoryConversationStore {
    private val conversations = ConcurrentHashMap<String, MutableList<Message>>()

    fun append(conversationId: String, message: Message) {
        conversations.computeIfAbsent(conversationId) { mutableListOf() }.add(message)
    }

    fun get(conversationId: String): List<Message> =
        conversations[conversationId]?.toList() ?: emptyList()
}

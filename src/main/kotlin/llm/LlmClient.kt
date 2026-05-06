package com.example.llm

import com.example.domain.Message

interface LlmClient {
    suspend fun complete(systemPrompt: String, history: List<Message>): String
}

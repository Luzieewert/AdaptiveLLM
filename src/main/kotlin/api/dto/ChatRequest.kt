package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatRequest(
    val conversationId: String,
    val userId: String,
    val topic: String,
    val mode: String,
    val message: String
)
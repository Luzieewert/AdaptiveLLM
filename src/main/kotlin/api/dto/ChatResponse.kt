package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    val conversationId: String,
    val reply: String
)
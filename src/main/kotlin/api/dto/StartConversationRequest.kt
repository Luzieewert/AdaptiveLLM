package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class StartConversationRequest(
    val userId: String,
    val conversationId: String,
    val topic: String,
    val mode: String
)
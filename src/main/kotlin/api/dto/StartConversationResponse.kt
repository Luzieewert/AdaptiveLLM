package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class StartConversationResponse(
    val conversationId: String,
    val openingMessage: String
)
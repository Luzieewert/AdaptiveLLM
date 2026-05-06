package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class OnboardingRequest(
    val userId: String,
    val conversationId: String,
    val topic: String,
    val mode: String,
    val responses: PersonaLikertResponses
)

@Serializable
data class PersonaLikertResponses(
    val tone: Int,
    val warmth: Int,
    val directness: Int,
    val mitigation: Int,
    val expressiveness: Int,
    val turnLength: Int
)
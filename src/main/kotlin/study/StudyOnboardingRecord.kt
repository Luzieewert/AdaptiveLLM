package com.example.study

import com.example.api.dto.PersonaLikertResponses
import kotlinx.serialization.Serializable

@Serializable
data class StudyOnboardingRecord(
    val userId: String,
    val conversationId: String,
    val topic: String,
    val mode: String,
    val responses: PersonaLikertResponses,
    val timestamp: Long = System.currentTimeMillis()
)
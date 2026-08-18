package com.example.study

import kotlinx.serialization.Serializable

@Serializable
data class StudyEvent(
    val timestamp: Long,
    val participantId: String,
    val stepType: String,
    val block: String? = null,
    val topic: String? = null,
    val mode: String? = null,
    val role: String? = null,
    val content: String? = null,
    val metadata: Map<String, String> = emptyMap()
)
package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class StudyEventRequest(
    val userId: String,
    val stepType: String,
    val block: String? = null,
    val topic: String? = null,
    val mode: String? = null,
    val responses: Map<String, String> = emptyMap()
)
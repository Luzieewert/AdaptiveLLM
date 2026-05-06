package com.example.study

import kotlinx.serialization.Serializable

@Serializable
data class StudyMessageRecord(
    val userId: String,
    val conversationId: String,
    val topic: String,
    val mode: String,
    val role: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
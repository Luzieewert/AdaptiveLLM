package com.example.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionnaireRequest(
    val userId: String,
    val block: String,
    val responses: Map<String, Int>
)
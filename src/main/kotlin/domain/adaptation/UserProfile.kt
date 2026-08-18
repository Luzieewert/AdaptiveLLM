package com.example.domain.adaptation

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val tone: Int = 4,            // 1 = very formal, 6 = very informal
    val warmth: Int = 4,          // 1 = very neutral, 6 = very empathetic
    val directness: Int = 4,      // 1 = very indirect, 6 = very straightforward
    val mitigation: Int = 4,      // 1 = low mitigation, 6 = high mitigation
    val expressiveness: Int = 4,  // 1 = low expressiveness, 6 = high expressiveness
    val turnLength: Int = 4       // 1 = very concise, 6 = very elaborative
)
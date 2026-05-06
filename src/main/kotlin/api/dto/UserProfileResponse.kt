package com.example.api.dto

import com.example.domain.adaptation.Tone
//import com.example.domain.adaptation.Verbosity
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileResponse(
    val userId: String,
    //val verbosity: Verbosity,
    val tone: Tone
)

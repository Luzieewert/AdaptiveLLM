package com.example.domain

enum class Role { USER, ASSISTANT }

data class Message(val role: Role, val content: String)

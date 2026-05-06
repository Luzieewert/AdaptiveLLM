package com.example.persistence

import com.example.domain.adaptation.UserProfile
import java.util.concurrent.ConcurrentHashMap

class InMemoryUserProfileStore {
    private val profiles = ConcurrentHashMap<String, UserProfile>()

    fun get(conversationId: String): UserProfile =
        profiles[conversationId] ?: UserProfile()

    fun set(conversationId: String, profile: UserProfile) {
        profiles[conversationId] = profile
    }
}


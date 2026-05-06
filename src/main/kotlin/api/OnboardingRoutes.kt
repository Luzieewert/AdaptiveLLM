package com.example.api

import com.example.api.dto.OnboardingRequest
import com.example.api.dto.OnboardingResponse
import com.example.domain.adaptation.PersonaMapper
import com.example.persistence.InMemoryUserProfileStore
import com.example.study.InMemoryStudyDataStore
import com.example.study.StudyOnboardingRecord
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.onboardingRoutes(
    profileStore: InMemoryUserProfileStore,
    studyDataStore: InMemoryStudyDataStore
) {
    post("/onboarding") {
        val req = call.receive<OnboardingRequest>()

        val profile = PersonaMapper.toUserProfile(req.responses)
        profileStore.set(req.conversationId, profile)

        studyDataStore.addOnboarding(
            StudyOnboardingRecord(
                userId = req.userId,
                conversationId = req.conversationId,
                topic = req.topic,
                mode = req.mode,
                responses = req.responses
            )
        )

        call.respond(HttpStatusCode.OK, OnboardingResponse(status = "ok"))
    }
}

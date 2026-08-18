package com.example.api

import com.example.api.dto.OnboardingRequest
import com.example.api.dto.OnboardingResponse
import com.example.domain.adaptation.PersonaMapper
import com.example.persistence.InMemoryUserProfileStore
import com.example.study.PersistentStudyDataStore
import com.example.study.StudyEvent
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.onboardingRoutes(
    profileStore: InMemoryUserProfileStore,
    studyDataStore: PersistentStudyDataStore
) {
    post("/onboarding") {
        val req = call.receive<OnboardingRequest>()

        val profile = PersonaMapper.toUserProfile(req.responses)
        profileStore.set(req.conversationId, profile)

        studyDataStore.appendEvent(
            StudyEvent(
                timestamp = System.currentTimeMillis(),
                participantId = req.userId,
                stepType = "onboarding",
                block = req.mode,
                topic = req.topic,
                mode = req.mode,
                metadata = mapOf(
                    "conversationId" to req.conversationId,
                    "tone" to req.responses.tone.toString(),
                    "warmth" to req.responses.warmth.toString(),
                    "directness" to req.responses.directness.toString(),
                    "mitigation" to req.responses.mitigation.toString(),
                    "expressiveness" to req.responses.expressiveness.toString(),
                    "turnLength" to req.responses.turnLength.toString()
                )
            )
        )

        call.respond(HttpStatusCode.OK, OnboardingResponse(status = "ok"))
    }
}

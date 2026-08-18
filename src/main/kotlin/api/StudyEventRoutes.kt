package com.example.api

import com.example.api.dto.StudyEventRequest
import com.example.study.PersistentStudyDataStore
import com.example.study.StudyEvent
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.studyEventRoutes(
    studyDataStore: PersistentStudyDataStore
) {
    post("/study-event") {
        val req = call.receive<StudyEventRequest>()

        studyDataStore.appendEvent(
            StudyEvent(
                timestamp = System.currentTimeMillis(),
                participantId = req.userId,
                stepType = req.stepType,
                block = req.block,
                topic = req.topic,
                mode = req.mode,
                metadata = req.responses
            )
        )

        call.respond(HttpStatusCode.OK, mapOf("status" to "ok"))
    }
}
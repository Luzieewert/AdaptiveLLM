package com.example.api

import com.example.api.dto.QuestionnaireRequest
import com.example.study.PersistentStudyDataStore
import com.example.study.StudyEvent
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.questionnaireRoutes(
    studyDataStore: PersistentStudyDataStore
) {
    post("/questionnaire") {
        val req = call.receive<QuestionnaireRequest>()

        studyDataStore.appendEvent(
            StudyEvent(
                timestamp = System.currentTimeMillis(),
                participantId = req.userId,
                stepType = "questionnaire",
                block = req.block,
                metadata = req.responses.mapValues { it.value.toString() }
            )
        )

        call.respond(HttpStatusCode.OK, mapOf("status" to "ok"))
    }
}
package com.example.api

import com.example.study.InMemoryStudyDataStore
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.exportRoutes(studyDataStore: InMemoryStudyDataStore) {

    get("/export/onboarding") {
        call.respond(studyDataStore.getAllOnboarding())
    }

    get("/export/messages") {
        call.respond(studyDataStore.getAllMessages())
    }

    get("/export/onboarding.csv") {
        val csv = buildString {
            appendLine("userId,conversationId,topic,mode,tone,warmth,directness,mitigation,expressiveness,turnLength,timestamp")
            studyDataStore.getAllOnboarding().forEach { record ->
                appendLine(
                    listOf(
                        escapeCsv(record.userId),
                        escapeCsv(record.conversationId),
                        escapeCsv(record.topic),
                        escapeCsv(record.mode),
                        record.responses.tone,
                        record.responses.warmth,
                        record.responses.directness,
                        record.responses.mitigation,
                        record.responses.expressiveness,
                        record.responses.turnLength,
                        record.timestamp
                    ).joinToString(",")
                )
            }
        }

        call.response.header(
            HttpHeaders.ContentDisposition,
            "attachment; filename=\"onboarding.csv\""
        )
        call.respondText(csv, ContentType.Text.CSV)
    }

    get("/export/messages.csv") {
        val csv = buildString {
            appendLine("userId,conversationId,topic,mode,role,content,timestamp")
            studyDataStore.getAllMessages().forEach { record ->
                appendLine(
                    listOf(
                        escapeCsv(record.userId),
                        escapeCsv(record.conversationId),
                        escapeCsv(record.topic),
                        escapeCsv(record.mode),
                        escapeCsv(record.role),
                        escapeCsv(record.content),
                        record.timestamp
                    ).joinToString(",")
                )
            }
        }

        call.response.header(
            HttpHeaders.ContentDisposition,
            "attachment; filename=\"messages.csv\""
        )
        call.respondText(csv, ContentType.Text.CSV)
    }
}

private fun escapeCsv(value: String): String {
    val escaped = value.replace("\"", "\"\"")
    return "\"$escaped\""
}
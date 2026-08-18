package com.example.api

import com.example.study.PersistentStudyDataStore
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.exportRoutes(studyDataStore: PersistentStudyDataStore) {

    get("/export/events") {

        call.response.header(
            HttpHeaders.ContentDisposition,
            "attachment; filename=\"study-events.jsonl\""
        )

        call.respondText(
            studyDataStore.readAllEventsText(),
            ContentType.Text.Plain
        )
    }
}

package com.example.api

import com.example.persistence.InMemoryUserProfileStore
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.profileRoutes(profileStore: InMemoryUserProfileStore) {

    get("/profile/{conversationId}") {
        val conversationId = call.parameters["conversationId"]
            ?: return@get call.respondText("missing conversationId", status = HttpStatusCode.BadRequest)

        val profile = profileStore.get(conversationId)
        call.respond(profile)
    }

    post("/profile/{conversationId}/reset") {
        val conversationId = call.parameters["conversationId"]
            ?: return@post call.respondText("missing conversationId", status = HttpStatusCode.BadRequest)

        profileStore.set(conversationId, com.example.domain.adaptation.UserProfile())
        call.respondText("ok")
    }
}


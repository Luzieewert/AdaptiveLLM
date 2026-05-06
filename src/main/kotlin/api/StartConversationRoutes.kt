package com.example.api

import com.example.api.dto.StartConversationRequest
import com.example.api.dto.StartConversationResponse
import com.example.domain.ChatService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.startConversationRoutes(chatService: ChatService) {
    post("/start-conversation") {
        val req = call.receive<StartConversationRequest>()

        val openingMessage = chatService.startConversation(
            conversationId = req.conversationId,
            userId = req.userId,
            topic = req.topic,
            mode = req.mode
        )

        call.respond(
            StartConversationResponse(
                conversationId = req.conversationId,
                openingMessage = openingMessage
            )
        )
    }
}
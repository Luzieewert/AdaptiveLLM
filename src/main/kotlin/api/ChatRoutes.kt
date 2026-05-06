package com.example.api

import com.example.api.dto.ChatRequest
import com.example.api.dto.ChatResponse
import com.example.domain.ChatService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.chatRoutes(chatService: ChatService) {
    post("/chat") {
        val req = call.receive<ChatRequest>()
        val reply = chatService.reply(
            conversationId = req.conversationId,
            userId = req.userId,
            userMessage = req.message,
            topic = req.topic,
            mode = req.mode
        )
        call.respond(ChatResponse(req.conversationId, reply))
    }
}
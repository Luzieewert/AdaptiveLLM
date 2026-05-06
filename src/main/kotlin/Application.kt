package com.example

import com.example.api.exportRoutes
import com.example.study.InMemoryStudyDataStore
import com.example.api.chatRoutes
import com.example.api.onboardingRoutes
import com.example.api.profileRoutes
import com.example.api.startConversationRoutes
import com.example.domain.ChatService
import com.example.llm.OpenAiLlmClient
import com.example.persistence.InMemoryConversationStore
import com.example.persistence.InMemoryUserProfileStore
import com.openai.models.ChatModel
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import io.ktor.server.http.content.*


fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {

    val conversationStore = InMemoryConversationStore()
    val profileStore = InMemoryUserProfileStore()
    val studyDataStore = InMemoryStudyDataStore()
    val llmClient = OpenAiLlmClient()
    val chatService = ChatService(conversationStore, profileStore, llmClient, studyDataStore)

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to (cause.message ?: "bad request"))
            )
        }
    }

    routing {
        get("/health") {
            call.respondText("ok")
        }
        onboardingRoutes(profileStore, studyDataStore)
        chatRoutes(chatService)
        startConversationRoutes(chatService)
        profileRoutes(profileStore)
        exportRoutes(studyDataStore)

        staticResources("/", "static")

    }
}




package com.example.study

import kotlinx.serialization.json.Json
import java.io.File

class PersistentStudyDataStore {

    private val json = Json { prettyPrint = false }

    private val dataDir = File("data/participants")

    init {
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }
    }

    fun appendEvent(event: StudyEvent) {

        val participantDir = File(dataDir, event.participantId)

        if (!participantDir.exists()) {
            participantDir.mkdirs()
        }

        val eventFile = File(participantDir, "events.jsonl")

        eventFile.appendText(
            json.encodeToString(event) + "\n"
        )
    }

    fun readAllEventsText(): String {
        val allEvents = dataDir
            .walkTopDown()
            .filter { it.isFile && it.name == "events.jsonl" }
            .flatMap { it.readLines() }
            .joinToString("\n")

        return allEvents
    }
}
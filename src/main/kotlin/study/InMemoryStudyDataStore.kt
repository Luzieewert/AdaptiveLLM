package com.example.study

import java.util.concurrent.CopyOnWriteArrayList

class InMemoryStudyDataStore {
    private val onboardingRecords = CopyOnWriteArrayList<StudyOnboardingRecord>()
    private val messageRecords = CopyOnWriteArrayList<StudyMessageRecord>()

    fun addOnboarding(record: StudyOnboardingRecord) {
        onboardingRecords.add(record)
    }

    fun addMessage(record: StudyMessageRecord) {
        messageRecords.add(record)
    }

    fun getAllOnboarding(): List<StudyOnboardingRecord> = onboardingRecords.toList()

    fun getAllMessages(): List<StudyMessageRecord> = messageRecords.toList()
}
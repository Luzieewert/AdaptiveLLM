# Adaptive LLM Study Prototype

Prototype developed for a Master's thesis pilot study on preference-based adaptation in human–AI interaction.

The application is a Kotlin/Ktor web application with a static HTML/JavaScript frontend. Participants complete short conversations with a neutral and a preference-adapted AI assistant. Onboarding preferences, chat messages, questionnaires, demographics, and feedback are stored as study events.

## Requirements

- JDK 17
- An OpenAI API key available through the environment expected by the OpenAI Java client

## Run locally

```bash
./gradlew run
```

The application is then available at `http://localhost:8080`.

## Tests

```bash
./gradlew test
```

## Data storage

Study data is written at runtime to `data/participants/<participantId>/events.jsonl`. The `data/` directory should not be committed when it contains participant data.


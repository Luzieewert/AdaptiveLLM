package com.example.domain.adaptation

import com.example.api.dto.PersonaLikertResponses

object PersonaMapper {

    private fun validate(score: Int): Int {
        require(score in 1..6) { "Likert score must be between 1 and 6" }
        return score
    }

    fun toUserProfile(r: PersonaLikertResponses): UserProfile {
        return UserProfile(
            tone = validate(r.tone),
            warmth = validate(r.warmth),
            directness = validate(r.directness),
            mitigation = validate(r.mitigation),
            expressiveness = validate(r.expressiveness),
            turnLength = validate(r.turnLength)
        )
    }
}

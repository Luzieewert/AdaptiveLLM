const rounds = [
    {
        id: 1,
        topic: "Hobbies and personal interests",
        mode: "neutral",
        needsOnboarding: false
    },
    {
        id: 2,
        topic: "Hobbies and personal interests",
        mode: "adaptive",
        needsOnboarding: true
    },
    {
        id: 3,
        topic: "Weather and everyday preferences",
        mode: "neutral",
        needsOnboarding: false
    },
    {
        id: 4,
        topic: "Weather and everyday preferences",
        mode: "adaptive",
        needsOnboarding: true
    }
];

function getCurrentRoundIndex() {
    return Number(localStorage.getItem("roundIndex") || "0");
}

function setCurrentRoundIndex(index) {
    localStorage.setItem("roundIndex", String(index));
}

function getCurrentRound() {
    return rounds[getCurrentRoundIndex()];
}

function getConversationId(userId) {
    const round = getCurrentRound();
    return `${userId}-round${round.id}`;
}

function moveToNextRound() {
    const nextIndex = getCurrentRoundIndex() + 1;

    if (nextIndex >= rounds.length) {
        localStorage.removeItem("conversationId");
        window.location.href = "complete.html";
        return;
    }

    setCurrentRoundIndex(nextIndex);
    localStorage.removeItem("conversationId");

    window.location.href = "round.html";
}

console.log("Frontend loaded.");
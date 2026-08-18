const topics = [
    "Hobbies and personal interests",
    "Weather and everyday preferences"
];

function getConditionOrder() {
    let order = localStorage.getItem("conditionOrder");

    if (!order) {
        order = Math.random() < 0.5 ? "neutral-first" : "adaptive-first";
        localStorage.setItem("conditionOrder", order);
    }

    return order;
}

function buildStudyFlow() {
    const neutralBlock = [
        {
            type: "blockIntro",
            block: "neutral"
        },
        {
            type: "chat",
            block: "neutral",
            mode: "neutral",
            topic: topics[0]
        },
        {
            type: "chat",
            block: "neutral",
            mode: "neutral",
            topic: topics[1]
        },
        {
            type: "questionnaire",
            block: "neutral"
        }
    ];

    const adaptiveBlock = [
        {
            type: "blockIntro",
            block: "adaptive"
        },
        {
            type: "onboarding",
            block: "adaptive",
            mode: "personalized",
            topic: topics[0]
        },
        {
            type: "chat",
            block: "adaptive",
            mode: "personalized",
            topic: topics[0]
        },
        {
            type: "onboarding",
            block: "adaptive",
            mode: "personalized",
            topic: topics[1]
        },
        {
            type: "chat",
            block: "adaptive",
            mode: "personalized",
            topic: topics[1]
        },
        {
            type: "questionnaire",
            block: "adaptive"
        }
    ];

    const order = getConditionOrder();

    return order === "neutral-first"
        ? [
            { type: "preQuestionnaire" },
            ...neutralBlock,
            ...adaptiveBlock,
            { type: "demographics" },
            { type: "feedback"},
            { type: "complete" }
        ]
        : [
            { type: "preQuestionnaire" },
            ...adaptiveBlock,
            ...neutralBlock,
            { type: "demographics" },
            { type: "feedback"},
            { type: "complete" }
        ];
}

function getCurrentStepIndex() {
    return Number(localStorage.getItem("stepIndex") || "0");
}

function setCurrentStepIndex(index) {
    localStorage.setItem("stepIndex", String(index));
}

function getCurrentStep() {
    return buildStudyFlow()[getCurrentStepIndex()];
}

function getCurrentRound() {
    return getCurrentStep();
}

function getBlockLabel(block) {
    const flow = buildStudyFlow();
    const firstBlock = flow.find(step => step.block)?.block;

    return block === firstBlock ? "Round A" : "Round B";
}

function getConversationId(userId) {
    const step = getCurrentStep();

    const safeTopic = step.topic
        ? step.topic.replaceAll(" ", "-").toLowerCase()
        : "no-topic";

    return `${userId}-${step.block || step.type}-${safeTopic}`;
}

function goToCurrentStepPage() {
    const step = getCurrentStep();

    if (!step) {
        window.location.href = "complete.html";
        return;
    }

    if (step.type === "preQuestionnaire") {
        window.location.href = "preQuestionnaire.html";
    } else if (step.type === "blockIntro") {
        window.location.href = "block.html";
    } else if (step.type === "onboarding") {
        window.location.href = "onboarding.html";
    } else if (step.type === "chat") {
        window.location.href = "round.html";
    } else if (step.type === "questionnaire") {
        window.location.href = "questionnaire.html";
    } else if (step.type === "demographics") {
        window.location.href = "demographics.html";
    } else if (step.type === "feedback") {
        window.location.href = "feedback.html";
    } else {
        window.location.href = "complete.html";
    }
}

function moveToNextStep() {
    const nextIndex = getCurrentStepIndex() + 1;
    setCurrentStepIndex(nextIndex);
    localStorage.removeItem("conversationId");
    goToCurrentStepPage();
}

console.log("Frontend loaded.");
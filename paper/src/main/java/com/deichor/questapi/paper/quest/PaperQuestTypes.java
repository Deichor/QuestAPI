package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.model.RequirementType;

public enum PaperQuestTypes implements RequirementType {
    BLOCK_PLACE("Block Place"),
    BLOCK_BREAK("Block Break");

    private final String name;

    PaperQuestTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}

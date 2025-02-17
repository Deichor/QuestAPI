package com.deichor.questapi.paper.quest.types;

import com.deichor.questapi.core.model.RequirementType;

public class BlockBreak implements RequirementType {

    private final String name = "Block Break";

    @Override
    public String getName() {
        return name;
    }
}

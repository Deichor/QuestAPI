package com.deichor.questapi.paper.quest.types;

import com.deichor.questapi.core.model.RequirementType;

public class BlockPlace implements RequirementType {

    private final String name = "Block Place";

    @Override
    public String getName() {
        return name;
    }
}

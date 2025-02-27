package com.deichor.questapi.paper.quest;

public enum BukkitOwnerTypes {
    PLAYER("bukkit_player");

    private final String type;

    BukkitOwnerTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

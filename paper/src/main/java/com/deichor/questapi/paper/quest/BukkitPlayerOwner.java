package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.model.QuestOwner;
import org.bukkit.entity.Player;

public class BukkitPlayerOwner implements QuestOwner<Player> {

    private final Player player;
    public BukkitPlayerOwner(Player player) {
        this.player = player;
    }

    @Override
    public Player getOwner() {
        return player;
    }
}

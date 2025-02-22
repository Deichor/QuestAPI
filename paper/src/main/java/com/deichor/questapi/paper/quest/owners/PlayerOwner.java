package com.deichor.questapi.paper.quest.owners;

import com.deichor.questapi.paper.quest.BukkitOwner;
import org.bukkit.entity.Player;

public class PlayerOwner implements BukkitOwner<Player> {

    private final Player player;

    public PlayerOwner(Player player) {
        this.player = player;
    }

    @Override
    public Player getOwner() {
        return player;
    }
}


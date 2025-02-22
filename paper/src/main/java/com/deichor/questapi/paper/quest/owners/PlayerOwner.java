package com.deichor.questapi.paper.quest.owners;

import com.deichor.questapi.paper.quest.BukkitOwner;
import org.bukkit.entity.Player;

public class PlayerOwner extends BukkitOwner<Player> {

    private final Player player;

    public PlayerOwner(Player player) {
        this.player = player;
    }

    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public String getOwnerType() {
        return "bukkit_player";
    }

    @Override
    public String serialize() {
        return player.getName();
    }

}


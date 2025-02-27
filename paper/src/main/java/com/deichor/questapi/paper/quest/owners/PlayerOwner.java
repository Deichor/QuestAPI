package com.deichor.questapi.paper.quest.owners;

import com.deichor.questapi.paper.quest.BukkitOwner;
import com.deichor.questapi.paper.quest.BukkitOwnerTypes;
import org.bukkit.entity.Player;

/**
 * Represents a player owner in Bukkit
 */
public class PlayerOwner implements BukkitOwner<Player> {

    private final Player player;

    /**
     * Creates a new player owner
     *
     * @param player The player
     */
    public PlayerOwner(Player player) {
        this.player = player;
    }

    @Override
    public Player getOwner() {
        return player;
    }

    @Override
    public String getOwnerType() {
        return BukkitOwnerTypes.PLAYER.name();
    }
    
    @Override
    public boolean isValid() {
        return player != null && player.isOnline();
    }
}


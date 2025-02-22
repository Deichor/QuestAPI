package com.deichor.questapi.paper;

import com.deichor.questapi.paper.quest.owners.PlayerOwner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaperListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        QuestHolder.removeAllQuestsFromOwner(new PlayerOwner(event.getPlayer()));
    }

}

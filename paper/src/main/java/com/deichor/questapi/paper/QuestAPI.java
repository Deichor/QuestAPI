package com.deichor.questapi.paper;

import org.bukkit.plugin.java.JavaPlugin;

public class QuestAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("QuestAPI enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("QuestAPI disabled!");
    }
}

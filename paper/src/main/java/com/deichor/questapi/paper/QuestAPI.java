package com.deichor.questapi.paper;

import com.deichor.questapi.paper.configuration.i18n;
import org.bukkit.plugin.java.JavaPlugin;

public class QuestAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("QuestAPI enabled!");

        //Locale API Register
        //TODO support other languages
        i18n.init();

    }

    @Override
    public void onDisable() {
        getLogger().info("QuestAPI disabled!");
    }
}

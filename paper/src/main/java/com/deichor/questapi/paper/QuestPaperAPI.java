package com.deichor.questapi.paper;

import com.deichor.questapi.core.QuestAPI;
import org.bukkit.plugin.Plugin;

public class QuestPaperAPI implements QuestAPI {

    private static QuestPaperAPI INSTANCE;

    public QuestPaperAPI(Plugin plugin){
        plugin.getServer().getPluginManager().registerEvents(new PaperListeners(), plugin);
    }

    public static void init(Plugin plugin){
        if(INSTANCE == null){
            INSTANCE = new QuestPaperAPI(plugin);
        }
    }

}

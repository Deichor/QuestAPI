package com.deichor.questapi.paper;

import com.deichor.questapi.core.QuestAPI;
import com.deichor.questapi.core.storage.StorageTypes;
import org.bukkit.plugin.Plugin;

public class QuestPaperAPI implements QuestAPI {

    private static QuestPaperAPI INSTANCE;
    private static StorageTypes storageType;

    public QuestPaperAPI(Plugin plugin, StorageTypes storageType){
        plugin.getServer().getPluginManager().registerEvents(new PaperListeners(), plugin);
        QuestPaperAPI.storageType = storageType;
    }

    public static void init(Plugin plugin, StorageTypes storageType){
        if(INSTANCE == null){
            INSTANCE = new QuestPaperAPI(plugin, storageType);
        }
    }

    @Override
    public StorageTypes getStorageType() {
        return storageType;
    }
}

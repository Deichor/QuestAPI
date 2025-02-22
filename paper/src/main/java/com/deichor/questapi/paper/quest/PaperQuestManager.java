package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.paper.events.OnQuestComplete;
import org.bukkit.Bukkit;

public class PaperQuestManager<BukkitOwner> extends QuestManager<BukkitOwner> {

    public PaperQuestManager(BaseQuest<BukkitOwner, ?> quest) {
        super(quest);
    }


    @Override
    public boolean finishQuest() {
        Bukkit.getPluginManager().callEvent(new OnQuestComplete(this.getQuest().getOwner()));
        return super.finishQuest();
    }

}

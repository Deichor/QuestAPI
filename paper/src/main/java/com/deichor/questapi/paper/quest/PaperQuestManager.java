package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.QuestManager;
import com.deichor.questapi.core.extend.BaseQuest;

public class PaperQuestManager<BukkitPlayerOwner> extends QuestManager<BukkitPlayerOwner> {

    public PaperQuestManager(BaseQuest<BukkitPlayerOwner, ?> quest) {
        super(quest);
    }

}

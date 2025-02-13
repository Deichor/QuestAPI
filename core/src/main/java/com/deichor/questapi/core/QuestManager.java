package com.deichor.questapi.core;

import com.deichor.questapi.core.model.Quest;

public class QuestManager {

    private Quest quest;

    public QuestManager(Quest quest) {
        this.quest = quest;
        quest.start();
    }
    
    
    

}

package com.deichor.questapi.core;

import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.core.model.QuestOwner;

public abstract class QuestManager {

    private final BaseQuest quest;

    public QuestManager(BaseQuest quest) {
        this.quest = quest;
        quest.start();
    }

    protected abstract void giveRewards();

    public boolean finishQuest(){
        if(quest.getRequirements().stream().allMatch(requirement -> requirement.isComplete())){
            quest.complete();
            return true;
        }
        return false;
    }
    
    protected BaseQuest getQuest() {
        return quest;
    }
}

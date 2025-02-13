package com.deichor.questapi.core;

import com.deichor.questapi.core.extend.BaseQuest;

public abstract class QuestManager {

    private BaseQuest quest;

    public QuestManager(BaseQuest quest) {
        this.quest = quest;
        quest.start();
    }

    protected abstract void giveReward();

    public boolean finishQuest(){
        if(quest.getRequirements().stream().allMatch(requirement -> requirement.isComplete())){
            quest.complete();
            giveReward();
            return true;
        }
        return false;
    }
    
    protected BaseQuest getQuest() {
        return quest;
    }
}

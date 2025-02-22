package com.deichor.questapi.core;

import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.core.model.QuestRequirement;

public abstract class QuestManager<O> {

    private final BaseQuest<O, ?> quest;

    public QuestManager(BaseQuest<O, ?> quest) {
        this.quest = quest;
        quest.start();
    }

    public void giveReward(){
        quest.getRewards().forEach(reward -> reward.giveReward(quest.getOwner()));
    }

    public boolean finishQuest(){
        if(quest.getRequirements().stream().allMatch(QuestRequirement::isComplete)){
            quest.complete();
            return true;
        }
        return false;
    }
    
    public BaseQuest<O, ?> getQuest() {
        return quest;
    }
}

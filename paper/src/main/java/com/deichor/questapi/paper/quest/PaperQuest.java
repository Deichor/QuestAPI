package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.extend.BaseQuest;
import com.deichor.questapi.core.model.QuestRequirement;

import java.util.List;

public class PaperQuest<O, R> extends BaseQuest<O, R> {

    O owner;
    List<QuestRequirement> requirement;

    public PaperQuest(O owner, List<QuestRequirement> requirement){
        this.owner = owner;
        this.requirement = requirement;
    }

}

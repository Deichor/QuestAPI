package com.deichor.questapi.paper.quest;

import com.deichor.questapi.core.model.QuestOwner;

public abstract class BukkitOwner<T> implements QuestOwner<T> {

    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public abstract String serialize();



}

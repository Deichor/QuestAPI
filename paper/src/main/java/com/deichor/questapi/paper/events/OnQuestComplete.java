package com.deichor.questapi.paper.events;

import com.deichor.questapi.core.model.QuestOwner;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class OnQuestComplete extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final QuestOwner<?> owner;

    public OnQuestComplete(QuestOwner<?> owner) {
        this.owner = owner;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public QuestOwner<?> getOwner() {
        return owner;
    }
}

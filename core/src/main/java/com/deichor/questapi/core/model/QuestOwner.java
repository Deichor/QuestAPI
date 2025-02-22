package com.deichor.questapi.core.model;

public interface QuestOwner<T> {
    T getOwner();
    String getOwnerId();
    String getOwnerType();
    String serialize(); //for database
}

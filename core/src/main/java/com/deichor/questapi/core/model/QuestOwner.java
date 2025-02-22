package com.deichor.questapi.core.model;

public interface QuestOwner<T> {
    T getOwner();
    String getOwnerType();
    String serialize();
    
    // Optional ID that will be set by the storage system
    default Long getId() {
        return null;
    }
    
    default void setId(Long id) {}
}

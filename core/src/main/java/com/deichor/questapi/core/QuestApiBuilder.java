package com.deichor.questapi.core;

import com.deichor.questapi.core.storage.StorageTypes;

public class QuestApiBuilder {

    private StorageTypes storageType;

    public QuestApiBuilder storageType(StorageTypes storageType) {
        return this;
    }

    //TODO: Implement the database
    public QuestApiBuilder database(){
        return this;
    }

    public static QuestApiBuilder builder() {
        return new QuestApiBuilder();
    }

}

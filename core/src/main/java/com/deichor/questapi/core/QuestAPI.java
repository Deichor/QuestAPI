package com.deichor.questapi.core;

import com.deichor.questapi.core.storage.StorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface QuestAPI {
    StorageManager getStorageManager();
    
    String getIdentifier();

    List<QuestAPI> REGISTERED_APIS = new ArrayList<>();

    static void registerAPI(QuestAPI api) {
        REGISTERED_APIS.add(api);
    }

    static void unregisterAPI(QuestAPI api) {
        REGISTERED_APIS.remove(api);
    }

    static List<QuestAPI> getRegisteredAPIs() {
        return new ArrayList<>(REGISTERED_APIS);
    }

    static Optional<QuestAPI> getAPI(String identifier) {
        return REGISTERED_APIS.stream()
                .filter(api -> api.getIdentifier().equals(identifier))
                .findFirst();
    }

    static <T extends QuestAPI> T getByIdentifier(String identifier, Class<T> apiType) {
        return getAPI(identifier)
                .filter(apiType::isInstance)
                .map(apiType::cast)
                .orElseThrow(() -> new IllegalStateException("No " + apiType.getSimpleName() + " found with identifier: " + identifier));
    }

    static <T extends QuestAPI> List<T> getAllAPIsOfType(Class<T> apiType) {
        return REGISTERED_APIS.stream()
                .filter(apiType::isInstance)
                .map(apiType::cast)
                .toList();
    }
}

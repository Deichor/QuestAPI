package com.deichor.questapi.localization;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalizationService {
    private static final LocalizationService INSTANCE = new LocalizationService();

    private final Map<String, LocaleManagerEntry<?, ?>> managers = new ConcurrentHashMap<>();

    private LocalizationService() {}

    public static LocalizationService getInstance() {
        return INSTANCE;
    }

    /**
     * Registers a new LocaleManager with the service
     * @param name Unique identifier for this manager
     * @param bundlePath Path to the resource bundle
     * @param manager The LocaleManager instance
     * @param classLoader ClassLoader to use for resource loading
     */
    public <T, P> void registerManager(
            String name,
            String bundlePath,
            LocaleManager<T, P> manager,
            ClassLoader classLoader
    ) {
        managers.put(name, new LocaleManagerEntry<>(manager, bundlePath, classLoader));
        manager.init(bundlePath, classLoader);
    }

    /**
     * Gets a registered LocaleManager by name
     */
    @SuppressWarnings("unchecked")
    public <T, P> LocaleManager<T, P> getManager(String name) {
        LocaleManagerEntry<?, ?> entry = managers.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("No LocaleManager registered with name: " + name);
        }
        return (LocaleManager<T, P>) entry.manager();
    }

    private record LocaleManagerEntry<T, P>(
            LocaleManager<T, P> manager,
            String bundlePath,
            ClassLoader classLoader
    ) {}
}

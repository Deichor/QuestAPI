package com.deichor.questapi.localization;

import dev.akkinoc.util.YamlResourceBundle;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * LocaleManager is responsible for managing translations for different locales.
 * It loads resource bundles and provides methods to translate keys into localized messages.
 *
 * @param <T> The type that the translated message will be deserialized into
 */
public abstract class LocaleManager<T, P> {
    private final Locale defaultLocale;
    private final List<Locale> supportedLocales;
    private final Map<Locale, ResourceBundle> bundles = new HashMap<>();
    private final String newLineSeperator;

    public LocaleManager(Locale defaultLocale, List<Locale> supportedLocales, String newLineSeperator) {
        this.defaultLocale = defaultLocale;
        this.supportedLocales = supportedLocales;
        this.newLineSeperator = newLineSeperator;
    }

    protected LocaleManager() {
        this(Locale.US, List.of(Locale.US), "\n");
    }

    void init(String bundlePath, ClassLoader classLoader) {
        for (Locale locale : supportedLocales) {
            bundles.put(locale, ResourceBundle.getBundle(
                    bundlePath,
                    locale,
                    classLoader,
                    YamlResourceBundle.Control.INSTANCE
            ));
        }
    }

    protected abstract T deserialize(String raw, P... placeholders);

    public T translate(Locale locale, @NotNull String key, P... placeholders) {
        if (!bundles.containsKey(locale)) {
            locale = defaultLocale;
        }
        String raw;
        try {
            Object obj = bundles.get(locale).getObject(key);
            if (obj instanceof String) {
                raw = (String) obj;
            } else if (obj instanceof String[]) {
                raw = mmConcatList((String[]) obj, newLineSeperator);
            } else {
                raw = "Error: OP-L02";
            }
        } catch (MissingResourceException e) {
            raw = "Error: OP-L01";
        }
        return deserialize(raw, placeholders);
    }

    private static String mmConcatList(String[] array, String newLineSeperator) {
        String raw = array[0];
        for (int i = 1; i < array.length; i++) {
            raw = raw.concat(newLineSeperator).concat(array[i]);
        }
        return raw;
    }
}
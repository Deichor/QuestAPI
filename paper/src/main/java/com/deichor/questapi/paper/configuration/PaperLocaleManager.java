package com.deichor.questapi.paper.configuration;

import com.deichor.questapi.localization.LocaleManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;
import java.util.Locale;

public class PaperLocaleManager extends LocaleManager<Component, TagResolver.Single> {

    public PaperLocaleManager() {
        super(Locale.ENGLISH, List.of(Locale.ENGLISH), "\n");
    }

    @Override
    protected Component deserialize(String raw, TagResolver.Single... placeholders) {
        return MiniMessage.miniMessage().deserialize(raw, placeholders);
    }
}

package by.lashkevich.web.controller.command.impl;

import java.util.Arrays;

public enum Languages {
    RUSSIAN("ru_RU"),
    ENGLISH("en_US");

    private final String localeName;

    Languages(String localeName) {
        this.localeName = localeName;
    }

    public String getLocaleName() {
        return localeName;
    }

    public static boolean isExistentLocale(String locale) {
        return Arrays.stream(Languages.values())
                .anyMatch(language -> language.getLocaleName().toUpperCase().equals(locale.toUpperCase()));
    }
}

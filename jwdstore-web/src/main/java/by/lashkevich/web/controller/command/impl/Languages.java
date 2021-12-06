package by.lashkevich.web.controller.command.impl;

import java.util.Arrays;

/**
 * The enum Languages.
 * @author Roman Lashkevich
 */
public enum Languages {
    /**
     * Russian languages.
     */
    RUSSIAN("ru_RU"),
    /**
     * English languages.
     */
    ENGLISH("en_US");

    private final String localeName;

    Languages(String localeName) {
        this.localeName = localeName;
    }

    /**
     * Gets locale name.
     *
     * @return the locale name
     */
    public String getLocaleName() {
        return localeName;
    }

    /**
     * Is existent locale boolean.
     *
     * @param locale the locale
     * @return the boolean
     */
    public static boolean isExistentLocale(String locale) {
        return Arrays.stream(Languages.values())
                .anyMatch(language -> language.getLocaleName().toUpperCase().equals(locale.toUpperCase()));
    }
}

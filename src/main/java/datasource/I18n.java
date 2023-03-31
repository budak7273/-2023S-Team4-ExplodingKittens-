package datasource;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum I18n {
    ENGLISH('e', Locale.ENGLISH),
    GERMAN('g', Locale.GERMAN);
    private final char localeRepresentingCharacter;
    private final Locale locale;
    private static Locale currentLocation = Locale.ENGLISH;

    I18n(char localeCharacter, Locale sourceLocale) {
        this.localeRepresentingCharacter = localeCharacter;
        this.locale = sourceLocale;
    }

    @Override
    public String toString() {
        return String.valueOf(localeRepresentingCharacter);
    }

    public static String getMessage(String i18nKey) {
        ResourceBundle messages = ResourceBundle.getBundle("message", currentLocation);
        try {
            return messages.getString(i18nKey);
        } catch (MissingResourceException e) {
            return i18nKey + "!!";
        }
    }

    public String localeSummaryString() {
        return String.format("[%s] - %s", localeRepresentingCharacter, locale.getDisplayName());
    }

    public static String switchLanguage(String entry) {
        for (I18n language : I18n.values()) {
            boolean localeNameMatch = entry.equalsIgnoreCase(language.locale.getDisplayLanguage());
            boolean localeCodeMatch = entry.equals(language.locale.getLanguage());
            boolean exactCharMatch = language.localeRepresentingCharacter == entry.charAt(0);
            if (exactCharMatch || localeNameMatch || localeCodeMatch) {
                currentLocation = language.locale;
                return language.locale.getDisplayLanguage();
            }
        }
        System.out.println(I18n.getMessage("InvalidLanguageSelected"));
        return ENGLISH.locale.getDisplayLanguage();
    }
}

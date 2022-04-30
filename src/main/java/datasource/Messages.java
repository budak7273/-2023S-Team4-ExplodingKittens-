package datasource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Messages {
    INVALID_MESSAGE {
        public String toString() {
            return "invalidMessage";
        }
    };



    static Locale currentLocation = Locale.GERMAN;
    public static String getMessage(final String key) {

        ResourceBundle messages =
                ResourceBundle.getBundle("message", currentLocation);
        String value = messages.getString(key);
        return value;
    }
}

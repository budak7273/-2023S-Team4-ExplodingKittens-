package datasource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Messages {
    INVALID_MESSAGE {
        public String toString() {
            return "invalidMessage";
        }
    };

    public static String getMessage(Locale currentLocation, String key){
        ResourceBundle messages =
                ResourceBundle.getBundle("src/main/resources/message", currentLocation);
        String value = messages.getString(key);
        return value;
    }
}

package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Do not instantiate a utility class");
    }

    public static List<String> enumValuesToStrings(Enum<?>[] values) {
        List<String> strings = new ArrayList<>();

        for (Enum<?> item : values) {
            strings.add(item.name());
        }
        return Collections.unmodifiableList(strings);
    }
}

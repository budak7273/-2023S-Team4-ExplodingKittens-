package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static Set<User> forUsers(User one, User two) {
        Set<User> users = new HashSet<>();
        users.add(one);
        users.add(two);
        return Collections.unmodifiableSet(users);
    }

    public static Set<User> forUsers(User one) {
        Set<User> users = new HashSet<>();
        users.add(one);
        return Collections.unmodifiableSet(users);
    }
}

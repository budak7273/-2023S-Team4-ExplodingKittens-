package System;

import java.util.*;

public class Setup {
    public Queue<User> createUsers(List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }

        if (names.size() > 10) {
            throw new IllegalArgumentException();
        }

        Set<String> checkDuplicateSet = new HashSet<String>(names);
        if (checkDuplicateSet.size() != names.size()) {
            throw new IllegalArgumentException();
        }


        Queue<User> queue = new LinkedList<User>();
        for (String name : names) {
            User user = new User();
            queue.add(user);
        }
        return queue;
    }
}

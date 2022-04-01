package System;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Setup {
    public Queue<User> createUsers(List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }
        Queue<User> queue = new LinkedList<User>();
        return queue;
    }
}

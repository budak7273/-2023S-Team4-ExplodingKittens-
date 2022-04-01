package System;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SetupTesting {
    @Test
    public void testCreateUsers_fromEmptyList() {
        Setup setup = new Setup();
        List<String> names = new ArrayList<>();
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.isEmpty());
    }
}

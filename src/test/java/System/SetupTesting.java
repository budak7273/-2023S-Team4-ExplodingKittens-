package System;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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

    @Test
    public void testCreateUsers_fromNull() {
        Setup setup = new Setup();
        Executable executable = () -> setup.createUsers(null);
        Assertions.assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void testCreateUsers_fromListOfSize2() {
        Setup setup = new Setup();
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 2);
    }

    @Test
    public void testCreateUsers_fromListOfSize10() {
        Setup setup = new Setup();
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 10);
    }

    @Test
    public void testCreateUsers_fromListOfSize11() {
        Setup setup = new Setup();
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            names.add("name" + i);
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsers_fromListWithDuplicates() {
        Setup setup = new Setup();
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("sameName");
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }
}

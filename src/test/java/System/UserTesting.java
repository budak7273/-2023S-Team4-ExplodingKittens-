package System;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTesting {
    @Test
    public void testUserConstructor_Name(){
        User user = new User("test1", false, new ArrayList<Card>());
        Assertions.assertEquals("test1", user.name);
        Assertions.assertNotEquals("test0", user.name);
    }
    @Test
    public void testCheckForPairs_EmptyHand() {
        User user = new User();
        List<String> names = new ArrayList<>();
//        Executable executable = () -> setup.createUsers(names);
//        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }
}

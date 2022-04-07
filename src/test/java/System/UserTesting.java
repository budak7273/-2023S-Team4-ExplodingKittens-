package System;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

public class UserTesting {
    @Test
    public void testUserConstructor_Default(){
        User user = new User();
        Assertions.assertEquals("", user.name);
        Assertions.assertTrue(user.alive);
    }

    @Test
    public void testUserConstructor_Name(){
        User user = new User("test1", false, new ArrayList<Card>());
        Assertions.assertEquals("test1", user.name);
        Assertions.assertNotEquals("test0", user.name);
    }

    @Test
    public void testUserConstructor_AliveOrDead(){
        User user = new User("test1", true, new ArrayList<Card>());
        User user2 = new User("test2", false, new ArrayList<Card>());
        Assertions.assertTrue(user.alive);
        Assertions.assertFalse(user2.alive);
    }

    @Test
    public void testUserConstructor_EmptyHand(){
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.hand);
    }

    @Test
    public void testUserConstructor_HandWithOneCard(){
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.hand);
        Assertions.assertEquals(1, user.hand.size());
        Assertions.assertEquals(card, user.hand.get(0));
    }

    @Test
    public void testUserConstructor_HandWithMultipleCard(){
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        Card card2 = new AttackCard();
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.hand);
        Assertions.assertEquals(2, user.hand.size());
        Assertions.assertNotEquals(card2, user.hand.get(0));
        Assertions.assertEquals(card2, user.hand.get(1));
    }

}

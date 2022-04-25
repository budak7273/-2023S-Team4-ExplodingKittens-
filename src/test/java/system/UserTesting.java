package system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;

public class UserTesting {
    @Test
    public void testUserConstructor_Default(){
        User user = new User();
        Assertions.assertEquals("", user.getName());
        Assertions.assertTrue(user.isAlive());
    }

    @Test
    public void testUserConstructor_Name(){
        User user = new User("test1", false, new ArrayList<Card>());
        Assertions.assertEquals("test1", user.getName());
        Assertions.assertNotEquals("test0", user.getName());
    }

    @Test
    public void testUserConstructor_AliveOrDead(){
        User user = new User("test1", true, new ArrayList<Card>());
        User user2 = new User("test2", false, new ArrayList<Card>());
        Assertions.assertTrue(user.isAlive());
        Assertions.assertFalse(user2.isAlive());
    }

    @Test
    public void testUserConstructor_EmptyHand(){
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
    }

    @Test
    public void testUserConstructor_HandWithOneCard(){
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(1, user.getHand().size());
        Assertions.assertEquals(card, user.getHand().get(0));
    }

    @Test
    public void testUserConstructor_HandWithMultipleCard(){
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        Card card2 = new AttackCard();
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(2, user.getHand().size());
        Assertions.assertNotEquals(card2, user.getHand().get(0));
        Assertions.assertEquals(card2, user.getHand().get(1));
    }

    @Test
    public void testCheckForSpecialEffectPotential_EmptyHand(){
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());

    }

    @Test
    public void testVerifySpecialEffectForCardsSelected_EmptyHandWithNonEmptyList(){
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(3);
        User user = new User("test1", false, list);
        Executable executable = () -> user.verifySpecialEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class,executable);

    }

}

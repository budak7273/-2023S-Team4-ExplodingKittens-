package system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.cards.*;

import java.util.ArrayList;

public class UserTesting {

    static final int ARBITRARY_COUNT_OF_SELECTED = 3;
    static final int MAX_HAND_SIZE = 120;
    @Test
    public void testUserConstructorDefault() {
        User user = new User();
        Assertions.assertEquals("", user.getName());
        Assertions.assertTrue(user.isAlive());
    }

    @Test
    public void testUserConstructorName() {
        User user = new User("test1", false, new ArrayList<Card>());
        Assertions.assertEquals("test1", user.getName());
        Assertions.assertNotEquals("test0", user.getName());
    }

    @Test
    public void testUserConstructorAliveOrDead() {
        User user = new User("test1", true, new ArrayList<Card>());
        User user2 = new User("test2", false, new ArrayList<Card>());
        Assertions.assertTrue(user.isAlive());
        Assertions.assertFalse(user2.isAlive());
    }

    @Test
    public void testUserConstructorEmptyHand() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
    }

    @Test
    public void testUserConstructorHandWithOneCard() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(1, user.getHand().size());
        Assertions.assertEquals(card, user.getHand().get(0));
    }

    @Test
    public void testUserConstructorHandWithMultipleCard() {
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
    public void testCheckForSpecialEffectPotentialEmptyHand() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());

    }

    @Test
    public void testCheckForSpecialEffectPotentialOneCard() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoCardsDifferentType() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new AttackCard();
        Card card2 = new AlterTheFutureCard();
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoMatchingCatCards() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new FeralCatCard();
        Card card2 = new CattermelonCard();
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoMatchingCatCards2() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new CattermelonCard();
        Card card2 = new CattermelonCard();
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoCatCardsNotMatching() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new HairyPotatoCatCard();
        Card card2 = new CattermelonCard();
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialThreeMatchingCatCards() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new CattermelonCard();
        Card card2 = new CattermelonCard();
        Card card3 = new FeralCatCard();
        list.add(card);
        list.add(card2);
        list.add(card3);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialMaxCardsNoPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            list.add(new AttackCard());
        }
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialMaxCardsOnePair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i==37||i==97){
                list.add(new CattermelonCard());
            }
            else list.add(new AttackCard());
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialMaxCardsTwoPairs() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i==7){
                list.add(new FeralCatCard());
            }

            if(i==33){
                list.add(new HairyPotatoCatCard());
            }

            if(i==67||i==118){
                list.add(new TacoCatCard());
            }

            else list.add(new AttackCard());
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialMaxCardsMaxPairs() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i%2==0){
                list.add(new CattermelonCard());
            }
            else list.add(new FeralCatCard());
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testVerifyEffectForCardsSelectedEmptyHandWithNonEmptyList() {
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(ARBITRARY_COUNT_OF_SELECTED);
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testVerifyEffectForCardsSelectedEmptyHandWithNullList() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(null);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testVerifyEffectForCardsSelectedHandWithNoCatCards() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new AttackCard());
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
        list.add(new AttackCard());
        selected.add(1);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize1HandWithMultipleIndex() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new AttackCard());
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandWithIndexDuplicated() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new AttackCard());
        list.add(new NopeCard());
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(0);
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectNonMatchingCat() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new RainbowRalphingCatCard());
        list.add(new CattermelonCard());
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectMatchingCat() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new FeralCatCard());
        list.add(new CattermelonCard());
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectMatchingCat2() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new CattermelonCard());
        list.add(new CattermelonCard());
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedMaxSizeHandSelectMatchingPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i==0) {
                list.add(new CattermelonCard());
            }
            else if(i==1) {
                list.add(new FeralCatCard());
            }
            else {
                list.add(new AttackCard());
            }
        }
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedMaxSizeHandSelectNonMatchingPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i==0) {
                list.add(new CattermelonCard());
            }
            else if(i==1) {
                list.add(new FeralCatCard());
            }
            else {
                list.add(new AttackCard());
            }
        }
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(3);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedMaxSizeHandContainTwoTriplesSelectNonMatchingPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i<3) {
                list.add(new RainbowRalphingCatCard());
            }
            else if(i<6) {
                list.add(new HairyPotatoCatCard());
            }
            else {
                list.add(new AttackCard());
            }
        }
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(3);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedMaxSizeHandContainOneTripleSelectMatchingPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i=0; i<MAX_HAND_SIZE; i++){
            if(i<3) {
                list.add(new RainbowRalphingCatCard());
            }
            else {
                list.add(new AttackCard());
            }
        }
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        selected.add(2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

}

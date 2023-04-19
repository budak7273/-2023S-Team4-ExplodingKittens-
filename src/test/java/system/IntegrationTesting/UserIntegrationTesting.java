package system.IntegrationTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.User;
import system.Card;
import java.util.ArrayList;

public class UserIntegrationTesting {

    static final int ARBITRARY_COUNT_OF_SELECTED = 3;
    static final int MAX_HAND_SIZE = 120;
    @Test
    public void testUserConstructorDefaultIntegrationTest() {
        User user = new User();
        Assertions.assertEquals("", user.getName());
        Assertions.assertTrue(user.isAlive());
    }

    @Test
    public void testUserConstructorNameIntegrationTest() {
        User user = new User("test1", false, new ArrayList<Card>());
        Assertions.assertEquals("test1", user.getName());
        Assertions.assertNotEquals("test0", user.getName());
    }

    @Test
    public void testUserConstructorAliveOrDeadIntegrationTest() {
        User user = new User("test1", true, new ArrayList<Card>());
        User user2 = new User("test2", false, new ArrayList<Card>());
        Assertions.assertTrue(user.isAlive());
        Assertions.assertFalse(user2.isAlive());
    }

    @Test
    public void testUserConstructorEmptyHandIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
    }

    @Test
    public void testUserConstructorHandWithOneCardIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(1, user.getHand().size());
        Assertions.assertEquals(card, user.getHand().get(0));
    }

    @Test
    public void testUserConstructorHandWithMultipleCardIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ATTACK);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(2, user.getHand().size());
        Assertions.assertEquals(card, user.getHand().get(0));
        Assertions.assertEquals(card, user.getHand().get(1));
    }

    @Test
    public void testCheckForSpecialEffectPotentialEmptyHandIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());

    }

    @Test
    public void testCheckForSpecialEffectPotentialOneCardIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoCardsDifferentTypeIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ALTER_THE_FUTURE);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoMatchingCatCardsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoMatchingCatCards2IntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.CATTERMELON);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoCatCardsNotMatchingIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.HAIRY_POTATO_CAT);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialThreeMatchingCatCardsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.CATTERMELON);
        Card card2 = new Card(CardType.CATTERMELON);
        Card card3 = new Card(CardType.FERAL_CAT);
        list.add(card);
        list.add(card2);
        list.add(card3);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialFeralMatchingCatCardsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.FERAL_CAT);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialMaxCardsNoPairIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            list.add(new Card(CardType.ATTACK));
        }
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialMaxCardsOnePairIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        final int minCattermelonCount = 37;
        final int maxCattermelonCount = 97;
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == minCattermelonCount || i == maxCattermelonCount) {
                list.add(new Card(CardType.CATTERMELON));
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialMaxCardsTwoPairsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        final int feralCatNumber = 7;
        final int hairyPotatoNumber = 33;
        final int tacoCatFirstNumber = 67;
        final int tacoCatSecondNumber = 118;
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == feralCatNumber) {
                list.add(new Card(CardType.FERAL_CAT));
            }

            if (i == hairyPotatoNumber) {
                list.add(new Card(CardType.HAIRY_POTATO_CAT));
            }

            if (i == tacoCatFirstNumber || i == tacoCatSecondNumber) {
                list.add(new Card(CardType.TACO_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialMaxCardsMaxPairsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i % 2 == 0) {
                list.add(new Card(CardType.CATTERMELON));
            } else {
                list.add(new Card(CardType.FERAL_CAT));
            }
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testVerifyEffectForSelectedEmptyHandWithNonEmptyListIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(new Card(CardType.ATTACK));
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testVerifyEffectForSelectedEmptyHandWithNullListIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(null);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testVerifyEffectForSelectedHandWithNoCatCardsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c = new Card(CardType.ATTACK);
        list.add(c);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
        Card c2 = new Card(CardType.ATTACK);
        list.add(c2);
        selected.add(c2);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectSize1HandWMultIndexIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c = new Card(CardType.ATTACK);
        list.add(c);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c);
        selected.add(new Card(CardType.ATTACK));
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandSelectNonMatchCatIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c1 = new Card(CardType.RAINBOW_RALPHING_CAT);
        Card c2 = new Card(CardType.CATTERMELON);
        list.add(c1);
        list.add(c2);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c1);
        selected.add(c2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandSelectMatchCatIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c1 = new Card(CardType.FERAL_CAT);
        Card c2 = new Card(CardType.CATTERMELON);
        list.add(c1);
        list.add(c2);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c1);
        selected.add(c2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandSelectMatchCat2IntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c1 = new Card(CardType.CATTERMELON);
        Card c2 = new Card(CardType.CATTERMELON);
        list.add(c1);
        list.add(c2);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c1);
        selected.add(c2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectMaxSizeHandSelectMatchPairIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c1 = new Card(CardType.CATTERMELON);
        Card c2 = new Card(CardType.FERAL_CAT);
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == 0) {
                list.add(c1);
            } else if (i == 1) {
                list.add(c2);
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c1);
        selected.add(c2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectMaxSizeHandSelectNonMatchPairIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card c1 = new Card(CardType.CATTERMELON);
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == 0) {
                list.add(c1);
            } else if (i == 1) {
                list.add(new Card(CardType.FERAL_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        final Card falseAdd = new Card(CardType.NOPE);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c1);
        selected.add(falseAdd);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelMaxHandCont2TripSelNonMatchPairIntegratTest() {
        final int rainbowRaphNumber = 3;
        final int hairPotatoNumber = 6;
        ArrayList<Card> list = new ArrayList<Card>();
        Card c1 = new Card(CardType.RAINBOW_RALPHING_CAT);
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i < rainbowRaphNumber) {
                list.add(c1);
            } else if (i < hairPotatoNumber) {
                list.add(new Card(CardType.HAIRY_POTATO_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        final Card falseAdd = new Card(CardType.NOPE);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c1);
        selected.add(falseAdd);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelMaxHandContOneTripleSelMatchPairIntegrationTest() {
        final int rainbowRaphNumber = 3;
        ArrayList<Card> list = new ArrayList<Card>();
        Card c = new Card(CardType.RAINBOW_RALPHING_CAT);
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i < rainbowRaphNumber) {
                list.add(c);
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(c);
        selected.add(c);
        selected.add(c);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

}

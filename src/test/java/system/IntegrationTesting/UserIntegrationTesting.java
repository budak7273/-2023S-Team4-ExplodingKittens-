package system.IntegrationTesting;

import datasource.CardType;
import datasource.Messages;
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
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(1, user.getHand().size());
        Assertions.assertEquals(card, user.getHand().get(0));
    }

    @Test
    public void testUserConstructorHandWithMultipleCardIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Card card2 = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
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
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoCardsDifferentTypeIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Card card2 = new Card(CardType.ALTER_THE_FUTURE, Messages.ALTER_DESC);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoMatchingCatCardsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.FERAL_CAT, Messages.FERAL_CAT_DESC);
        Card card2 = new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoMatchingCatCards2IntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC);
        Card card2 = new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialTwoCatCardsNotMatchingIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.HAIRY_POTATO_CAT,
                Messages.HAIRY_POTATO_CAT_DESC);

        Card card2 = new Card(CardType.CATTERMELON,
                Messages.CATTERMELON_DESC);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialThreeMatchingCatCardsIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC);
        Card card2 = new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC);
        Card card3 = new Card(CardType.FERAL_CAT, Messages.FERAL_CAT_DESC);
        list.add(card);
        list.add(card2);
        list.add(card3);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testCheckForSpecialEffectPotentialMaxCardsNoPairIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            list.add(new Card(CardType.ATTACK, Messages.ATTACK_DESC));
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
                list.add(new Card(CardType.CATTERMELON,
                        Messages.CATTERMELON_DESC));
            } else {
                list.add(new Card(CardType.ATTACK,
                        Messages.ATTACK_DESC));
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
                list.add(new Card(CardType.FERAL_CAT,
                        Messages.FERAL_CAT_DESC));
            }

            if (i == hairyPotatoNumber) {
                list.add(new Card(CardType.HAIRY_POTATO_CAT,
                        Messages.HAIRY_POTATO_CAT_DESC));
            }

            if (i == tacoCatFirstNumber || i == tacoCatSecondNumber) {
                list.add(new Card(CardType.TACO_CAT,
                        Messages.TACO_CAT_DESC));
            } else {
                list.add(new Card(CardType.ATTACK,
                        Messages.ATTACK_DESC));
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
                list.add(new Card(CardType.CATTERMELON,
                        Messages.CATTERMELON_DESC));
            } else {
                list.add(new Card(CardType.FERAL_CAT,
                        Messages.FERAL_CAT_DESC));
            }
        }
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void
    testVerifyEffectForSelectedEmptyHandWithNonEmptyListIntegrationTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(ARBITRARY_COUNT_OF_SELECTED);
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
        list.add(new Card(CardType.ATTACK, Messages.ATTACK_DESC));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
        list.add(new Card(CardType.ATTACK, Messages.ATTACK_DESC));
        selected.add(1);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectSize1HandWMultIndexIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.ATTACK, Messages.ATTACK_DESC));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandWIndexDupIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.ATTACK, Messages.ATTACK_DESC));
        list.add(new Card(CardType.NOPE, Messages.NOPE_DESC));

        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(0);
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.verifyEffectForCardsSelected(selected);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandSelectNonMatchCatIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.RAINBOW_RALPHING_CAT,
                Messages.RAINBOW_CAT_DESC));
        list.add(new Card(CardType.CATTERMELON,
                Messages.CATTERMELON_DESC));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandSelectMatchCatIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.FERAL_CAT, Messages.FERAL_CAT_DESC));
        list.add(new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectSize2HandSelectMatchCat2IntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC));
        list.add(new Card(CardType.CATTERMELON, Messages.CATTERMELON_DESC));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectMaxSizeHandSelectMatchPairIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == 0) {
                list.add(new Card(CardType.CATTERMELON,
                        Messages.CATTERMELON_DESC));
            } else if (i == 1) {
                list.add(new Card(CardType.FERAL_CAT,
                        Messages.FERAL_CAT_DESC));
            } else {
                list.add(new Card(CardType.ATTACK,
                        Messages.ATTACK_DESC));
            }
        }
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelectMaxSizeHandSelectNonMatchPairIntegratTest() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == 0) {
                list.add(new Card(CardType.CATTERMELON,
                        Messages.CATTERMELON_DESC));
            } else if (i == 1) {
                list.add(new Card(CardType.FERAL_CAT,
                        Messages.FERAL_CAT_DESC));
            } else {
                list.add(new Card(CardType.ATTACK,
                        Messages.ATTACK_DESC));
            }
        }
        final int falseAdd = 3;
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
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
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i < rainbowRaphNumber) {
                list.add(new Card(CardType.RAINBOW_RALPHING_CAT,
                        Messages.RAINBOW_CAT_DESC));
            } else if (i < hairPotatoNumber) {
                list.add(new Card(CardType.HAIRY_POTATO_CAT,
                        Messages.HAIRY_POTATO_CAT_DESC));
            } else {
                list.add(new Card(CardType.ATTACK,
                        Messages.ATTACK_DESC));
            }
        }
        final int falseAdd = 3;
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(falseAdd);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForSelMaxHandContOneTripleSelMatchPairIntegrationTest() {
        final int rainbowRaphNumber = 3;
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i < rainbowRaphNumber) {
                list.add(new Card(CardType.RAINBOW_RALPHING_CAT,
                        Messages.RAINBOW_CAT_DESC));
            } else {
                list.add(new Card(CardType.ATTACK, Messages.ATTACK_DESC));
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

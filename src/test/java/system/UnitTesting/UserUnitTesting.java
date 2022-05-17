package system.UnitTesting;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import presentation.GamePlayer;
import system.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static system.UnitTesting.GameStateUnitTesting.MAX_USER_COUNT;

public class UserUnitTesting {

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
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(1, user.getHand().size());
        Assertions.assertEquals(card, user.getHand().get(0));
    }

    @Test
    public void testUserConstructorHandWithOneCardRemoveOne() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        user.removeCard(card);
        list.remove(card);
        Assertions.assertEquals(list, user.getHand());
        Assertions.assertEquals(0, user.getHand().size());
    }

    @Test
    public void testUserConstructorHandWithMultipleCard() {
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
    public void testCheckForSpecialEffectPotentialEmptyHand() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());

    }

    @Test
    public void testCheckForSpecialEffectPotentialOneCard() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoCardsDifferentType() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ALTER_THE_FUTURE);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoMatchingCatCards() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoMatchingCatCards2() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.CATTERMELON);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoCatCardsNotMatching() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.HAIRY_POTATO_CAT);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialThreeMatchingCatCards() {
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
    public void testCheckForSpecialEffectPotentialMaxCardsNoPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            list.add(new Card(CardType.ATTACK));
        }
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForSpecialEffectPotential());
    }

    @Test
    public void testCheckForSpecialEffectPotentialMaxCardsOnePair() {
        final int minCattermelonCount = 37;
        final int maxCattermelonCount = 97;
        ArrayList<Card> list = new ArrayList<Card>();
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
    public void testCheckForSpecialEffectPotentialMaxCardsTwoPairs() {
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
    public void testCheckForSpecialEffectPotentialMaxCardsMaxPairs() {
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
    public void testVerifyEffectForCardsSelectedEmptyHandWithEmptyList() {
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Integer> selected = new ArrayList<>();
        User user = new User("test1", false, list);
        user.verifyCardsSelected(selected);
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
        list.add(new Card(CardType.ATTACK));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
        list.add(new Card(CardType.ATTACK));
        selected.add(1);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize1HandWithMultipleIndex() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.ATTACK));
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
        list.add(new Card(CardType.ATTACK));
        list.add(new Card(CardType.NOPE));
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
    testVerifyEffectForCardsSelectedSize2HandSelectNonMatchingCat() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.RAINBOW_RALPHING_CAT));
        list.add(new Card(CardType.CATTERMELON));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectMatchingCat() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.FERAL_CAT));
        list.add(new Card(CardType.CATTERMELON));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectMatchingCat2() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.CATTERMELON));
        list.add(new Card(CardType.CATTERMELON));
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void
    testVerifyEffectForCardsSelectedMaxSizeHandSelectMatchingPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == 0) {
                list.add(new Card(CardType.CATTERMELON));
            } else if (i == 1) {
                list.add(new Card(CardType.FERAL_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
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
    testVerifyEffectForCardsSelectedMaxSizeHandSelectNonMatchingPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i == 0) {
                list.add(new Card(CardType.CATTERMELON));
            } else if (i == 1) {
                list.add(new Card(CardType.FERAL_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
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
    testVerifyEffectForCardsSelectMaxHandContainTwoTriplesSelectNonMatchPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        final int rainbowRaphNumber = 3;
        final int hairPotatoNumber = 6;
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i < rainbowRaphNumber) {
                list.add(new Card(CardType.RAINBOW_RALPHING_CAT));
            } else if (i < hairPotatoNumber) {
                list.add(new Card(CardType.HAIRY_POTATO_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
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
    public void testVerifyEffectForCardsSelectMaxHandContOneTripSelMatchPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        final int rainbowRaphNumber = 3;
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            if (i < rainbowRaphNumber) {
                list.add(new Card(CardType.RAINBOW_RALPHING_CAT));
            } else {
                list.add(new Card(CardType.ATTACK));
            }
        }
        ArrayList<Integer> selected = new ArrayList<>();
        selected.add(0);
        selected.add(1);
        selected.add(2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testPlayerDiesNoDefuse() {
        User user = new User("test", false, new ArrayList<>());
        user.attemptToDie();
        Assertions.assertFalse(user.isAlive());
    }

    @Test
    public void testPlayerDiesWithDefuse() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(CardType.DEFUSE));

        User user = new User("test", false, hand);
        user.attemptToDie();
        Assertions.assertTrue(user.isAlive());
        Assertions.assertTrue(hand.isEmpty());
    }

    @Test
    public void testExecuteFavorOnUserLastInQueue() {
        Queue<User> pq = new LinkedList<>();
        Card c = new Card(CardType.ATTACK);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            User user = new User();
            pq.add(user);
        }
        User targetUser = new User();
        targetUser.addCard(c);
        pq.add(targetUser);

        GamePlayer gp = EasyMock.createMock(GamePlayer.class);
        EasyMock.expect(gp.inputForStealCard(targetUser)).andReturn(0);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);

        EasyMock.replay(gp);
        EasyMock.replay(deckMock);
        GameState gameState = new GameState(pq, gp, deckMock);
        gameState.executeFavorOn(targetUser);

        Assertions.assertFalse(targetUser.getHand().contains(c));
        Assertions.assertEquals(0, targetUser.getHand().size());

        EasyMock.verify(gp);
        EasyMock.verify(deckMock);
    }

}

package system.UnitTesting;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import presentation.GameWindow;
import system.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static system.UnitTesting.GameStateUnitTesting.MAX_USER_COUNT;

public class UserUnitTesting {

    static final Card ARBITRARY_CARD_OF_SELECTED = new Card(CardType.FAVOR);
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
    public void testUserConstructorHandWithOneCardRemoveByIndex() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        Card cardToRemove = user.getCardFromHand(0);
        user.removeCard(cardToRemove);
        Assertions.assertEquals(new ArrayList<>(), user.getHand());
        Assertions.assertTrue(user.isEmptyHand());
    }

    @Test
    public void testUserConstructorHandWithNoCardRemoveByIndex() {
        ArrayList<Card> list = new ArrayList<Card>();
        User user = new User("test1", false, list);
        Executable executable =
                () -> user.removeCard(user.getCardFromHand(0));
        Assertions.assertThrows(IndexOutOfBoundsException.class, executable);
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
        Assertions.assertFalse(user.checkForCatCardEffects());

    }

    @Test
    public void testCheckForSpecialEffectPotentialOneCard() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        list.add(card);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForCatCardEffects());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoCardsDifferentType() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ALTER_THE_FUTURE);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForCatCardEffects());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoMatchingCatCards() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForCatCardEffects());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoMatchingCatCards2() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.CATTERMELON);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkForCatCardEffects());
    }

    @Test
    public void testCheckForSpecialEffectPotentialTwoCatCardsNotMatching() {
        ArrayList<Card> list = new ArrayList<Card>();
        Card card = new Card(CardType.HAIRY_POTATO_CAT);
        Card card2 = new Card(CardType.CATTERMELON);
        list.add(card);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForCatCardEffects());
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
        Assertions.assertTrue(user.checkForCatCardEffects());
    }

    @Test
    public void testCheckForSpecialEffectPotentialMaxCardsNoPair() {
        ArrayList<Card> list = new ArrayList<Card>();
        for (int i = 0; i < MAX_HAND_SIZE; i++) {
            list.add(new Card(CardType.ATTACK));
        }
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkForCatCardEffects());
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
        Assertions.assertTrue(user.checkForCatCardEffects());
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
        Assertions.assertTrue(user.checkForCatCardEffects());
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
        Assertions.assertTrue(user.checkForCatCardEffects());
    }

    @Test
    public void testVerifyEffectForCardsSelectedEmptyHandWithEmptyList() {
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Card> selected = new ArrayList<>();
        User user = new User("test1", false, list);
        user.verifyCardsSelected(selected);
    }

    @Test
    public void testVerifyEffectForCardsSelectedEmptyHandWithNonEmptyList() {
        ArrayList<Card> list = new ArrayList<Card>();
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(ARBITRARY_CARD_OF_SELECTED);
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
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
        list.add(new Card(CardType.ATTACK));
        selected.add(list.get(1));
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize1HandWithMultipleIndex() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.ATTACK));
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        selected.add(list.get(0));
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
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        selected.add(list.get(1));
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectMatchingCat() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.FERAL_CAT));
        list.add(new Card(CardType.CATTERMELON));
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        selected.add(list.get(1));
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.verifyEffectForCardsSelected(selected));
    }

    @Test
    public void testVerifyEffectForCardsSelectedSize2HandSelectMatchingCat2() {
        ArrayList<Card> list = new ArrayList<Card>();
        list.add(new Card(CardType.CATTERMELON));
        list.add(new Card(CardType.CATTERMELON));
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        selected.add(list.get(1));
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
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        selected.add(list.get(1));
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
        final Card falseAdd = list.get(2);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
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
        final Card falseAdd = list.get(3);
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
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
        ArrayList<Card> selected = new ArrayList<>();
        selected.add(list.get(0));
        selected.add(list.get(1));
        selected.add(list.get(2));
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
    public void testPlayerNopesNoNope() {
        User user = new User("test", false, new ArrayList<>());
        boolean result = user.attemptToNope();

        Assertions.assertFalse(result);
    }

    @Test
    public void testPlayerNopesWithNope() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(CardType.NOPE));

        User user = new User("test", false, hand);
        boolean result = user.attemptToNope();

        Assertions.assertTrue(result);
        Assertions.assertTrue(hand.isEmpty());
    }

    @Test
    public void testPlayerNopesWithMultipleNopes() {
        ArrayList<Card> hand = new ArrayList<>();
        hand.add(new Card(CardType.NOPE));
        hand.add(new Card(CardType.NOPE));

        User user = new User("test", false, hand);
        boolean result = user.attemptToNope();

        Assertions.assertEquals(1, hand.size());
        Assertions.assertTrue(result);
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

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        EasyMock.expect(gw.inputForStealCard(targetUser)).andReturn(0);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " took a Attack from  via a Favor\n");
        EasyMock.expectLastCall();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " took a Attack from  via a Favor\n");
        EasyMock.expectLastCall();
        EasyMock.replay(gw);
        EasyMock.replay(deckMock);
        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.executeFavorOn(targetUser);

        Assertions.assertFalse(targetUser.getHand().contains(c));
        Assertions.assertEquals(0, targetUser.getHand().size());

        EasyMock.verify(gw);
        EasyMock.verify(deckMock);
    }

    @Test
    public void
    testCheckCatPairMatchOfTwoNonCatCardsNotMatch() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.NOPE);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfTwoNonCatCardsThatMatch() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ATTACK);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfMixCards1() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.ATTACK);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfMixCards2() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.CATTERMELON);
        Card card2 = new Card(CardType.ATTACK);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfNonFeralCatCards() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.CATTERMELON);
        Card card2 = new Card(CardType.RAINBOW_RALPHING_CAT);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertFalse(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfCatCardsWithOneFeral() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.RAINBOW_RALPHING_CAT);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfTwoMatchingNonFeralCards() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.RAINBOW_RALPHING_CAT);
        Card card2 = new Card(CardType.RAINBOW_RALPHING_CAT);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkCatPairMatch(card1, card2));
    }

    @Test
    public void
    testCheckCatPairMatchOfTwoFeralCards() {
        ArrayList<Card> list = new ArrayList<>();
        Card card1 = new Card(CardType.FERAL_CAT);
        Card card2 = new Card(CardType.FERAL_CAT);
        list.add(card1);
        list.add(card2);
        User user = new User("test1", false, list);
        Assertions.assertTrue(user.checkCatPairMatch(card1, card2));
    }

}

package system;

import datasource.CardType;
import datasource.Messages;

import java.util.*;

public class DrawDeck {
    private final Random random;
    private Deque<Card> cards;

    public DrawDeck(List<Card> cardList, Random inputRandom) {
        this.cards = new LinkedList<>(cardList);
        this.random = inputRandom;
    }

    // For tests that do not care about the random seed
    public DrawDeck(List<Card> cardList) {
        this.cards = new LinkedList<>(cardList);
        this.random = new Random();
    }

    public boolean drawCard(final User drawingUser) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.pop();

        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            return true;
        } else {
            drawingUser.addCard(drawnCard);
            return false;
        }
    }

    public boolean drawFromBottomForUser(final User currentUser) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.removeLast();

        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            return true;
        } else {
            currentUser.addCard(drawnCard);
            return false;
        }
    }

    public List<Card> drawThreeCardsFromTop() {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }

        ArrayList<Card> top = new ArrayList<>();
        final int maxTop = 3;

        while (cards.size() > 0 && top.size() < maxTop) {
            top.add(cards.pop());
        }

        return top;
    }

    public void addCardToTop(Card card) {
        cards.push(card);
    }

    public boolean shuffle() {
        Collections.shuffle((LinkedList<Card>) this.cards, random);
        return true;
    }

    public int getDeckSize() {
        return cards.size();
    }

    public int getDefuseCount() {
        int defuseCount = 0;
        for (Card card : cards) {
            if (card.getType() == CardType.DEFUSE) {
                defuseCount++;
            }
        }
        return defuseCount;
    }

    public List<Card> getCardsAsList() {
        List<Card> toReturn = new ArrayList<>();
        toReturn.addAll(this.cards);
        return toReturn;
    }

    public void addExplodingKittenAtLocation(Integer location) {
        Deque<Card> newCards = new LinkedList<>();
        for (int i = 0; i < location; i++) {
            newCards.addLast(cards.pop());
        }
        newCards.add(new Card(CardType.EXPLODING_KITTEN));
        newCards.addAll(cards);
        cards = newCards;
    }
}

package system;

import datasource.CardType;
import datasource.Messages;

import java.util.*;

public class DrawDeck {
    private Deque<Card> cards;

    public DrawDeck(List<Card> cardList) {
        Deque<Card> cardsCopy = new LinkedList<>();
        cardsCopy.addAll(cardList);
        cards = cardsCopy;
    }

    public void drawCard(User drawingUser) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.pop();
        drawingUser.addCard(drawnCard);
    }

    public void drawFromBottomForUser(User currentUser) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.removeLast();
        currentUser.addCard(drawnCard);
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

    public void shuffle() {
        Collections.shuffle((LinkedList<Card>) this.cards);
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
}

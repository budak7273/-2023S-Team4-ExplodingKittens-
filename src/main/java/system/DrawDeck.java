package system;

import datasource.CardType;
import datasource.Messages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawDeck {
    private List<Card> cards;

    public DrawDeck(final List<Card> cardList) {
        List<Card> cardsCopy = new ArrayList<>();
        cardsCopy.addAll(cardList);
        cards = cardsCopy;
    }
    public int getDeckSize() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void prependCard(Card card) { cards.add(0, card); }

    public void drawCard(User drawingUser) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.remove(0);
        drawingUser.addCard(drawnCard);
    }

    public List<Card> getCards() {
        List<Card> toReturn = new ArrayList<>();
        toReturn.addAll(this.cards);
        return toReturn;
    }

    public void drawInitialCard(User drawer) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }

        Card drawnCard = cards.remove(0);
        while (drawnCard.getType().equals(CardType.EXPLODING_KITTEN)) {
            cards.add(cards.size(), drawnCard);
            drawnCard = cards.remove(0);
        }
        drawer.addCard(drawnCard);
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
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

    public void drawFromBottomForUser(User currentUser) {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.remove(cards.size() - 1);
        currentUser.addCard(drawnCard);
    }

    public List<Card> getTopOfDeck() {
        if (cards.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_DRAW_DECK);
            throw new RuntimeException(msg);
        }

        ArrayList<Card> top = new ArrayList<>();

        while (cards.size() > 0 && top.size() < 3) {
            top.add(cards.remove(0));
        }

        return top;
    }
}

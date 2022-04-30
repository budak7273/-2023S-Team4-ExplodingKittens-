package system;

import datasource.CardType;
import system.cards.DefuseCard;
import system.cards.ExplodingCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawDeck {
    private List<Card> cards;

    public DrawDeck() {
        cards = new ArrayList<>();
    }
    public int getDeckSize() {
        return cards.size();
    }

    public void addCard(final Card card) {
        cards.add(card);
    }

    public boolean drawCard(final User drawingUser) {
        if (cards.isEmpty()) {
            String msg = "Draw deck is empty, the game was set up improperly.";
            throw new RuntimeException(msg);
        }
        Card drawnCard = cards.remove(0);

        if (drawnCard.getType() == CardType.EXPLODING_KITTEN) {
            drawingUser.die();
            return true;
        } else {
            drawingUser.addCard(drawnCard);
            return false;
        }
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void drawInitialCard(final User drawer) {
        if (cards.isEmpty()) {
            String msg = "Draw deck is empty, the game was set up improperly.";
            throw new RuntimeException(msg);
        }

        Card drawnCard = cards.remove(0);
        while (drawnCard.getName().equals("Exploding Kitten")) {
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
}

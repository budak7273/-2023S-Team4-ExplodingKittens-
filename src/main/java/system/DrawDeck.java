package system;

import java.util.ArrayList;
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

    public void drawCard(final User drawingUser) {
        if (cards.isEmpty()) {
            throw new RuntimeException("Draw deck is empty, the game was set up improperly.");
        }
        Card drawnCard = cards.remove(0);
        drawingUser.addCard(drawnCard);
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void drawInitialCard(User drawer) {
        if (cards.isEmpty()) {
            throw new RuntimeException("Draw deck is empty, the game was set up improperly.");
        }

        Card drawnCard = cards.remove(0);
        while (drawnCard.getName().equals("Exploding Kitten")) {
            cards.add(cards.size(), drawnCard);
            drawnCard = cards.remove(0);
        }
        drawer.addCard(drawnCard);
    }
}

package System;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class DrawDeck {
    List<Card> cards;

    public DrawDeck() {
        cards = new ArrayList<Card>();
    }
    public int getDeckSize() {
        return cards.size();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void drawCard() {
        System.out.println("TODO: Draw card");
    }
}

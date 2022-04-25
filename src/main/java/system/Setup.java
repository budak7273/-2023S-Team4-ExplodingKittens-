package System;

import DataSource.CardCSVParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Setup {
    int numOfPlayers;

    public Setup(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public Queue<User> createUsers(List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }

        if (names.size() < 2 || names.size() > 10) {
            throw new IllegalArgumentException();
        }

        Set<String> checkDuplicateSet = new HashSet<>(names);
        if (checkDuplicateSet.size() != names.size()) {
            throw new IllegalArgumentException();
        }

        Queue<User> queue = new LinkedList<>();
        for (String name : names) {
            User user = new User(name);
            queue.add(user);
        }
        return queue;
    }

    public DrawDeck createDrawDeck(File cardInfoFile) {
        DrawDeck drawDeck = new DrawDeck();
        CardCSVParser parser = new CardCSVParser(cardInfoFile);
        List<Card> cardList;
        if (numOfPlayers >= 2 && numOfPlayers <= 3) {
            cardList = parser.generateListOfCards(true, false);
        } else if (numOfPlayers >= 4 && numOfPlayers <= 7) {
            cardList = parser.generateListOfCards(false, true);
        } else {
            cardList = parser.generateListOfCards(true, true);
        }
        for (Card card : cardList) {
            drawDeck.addCard(card);
        }
        return drawDeck;
    }

    public DiscardDeck createDiscardDeck(){ return new DiscardDeck();   }

}

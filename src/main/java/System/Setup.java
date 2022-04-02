package System;

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
            User user = new User();
            queue.add(user);
        }
        return queue;
    }

    public DrawDeck createDrawDeck(File cardInfoFile) {
        DrawDeck drawDeck = new DrawDeck();
        try {
            Scanner cardInfoScanner = new Scanner(cardInfoFile);
            drawDeck = createDrawDeckUsingScanner(cardInfoScanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return drawDeck;
    }

    private DrawDeck createDrawDeckUsingScanner(Scanner cardInfoScanner) {
        DrawDeck drawDeck = new DrawDeck();
        int numOfCardsUntilRequiredCountIsReached = 113;
        while (cardInfoScanner.hasNext()) {
            String cardInfo = cardInfoScanner.next();
            String[] cardProperties = cardInfo.split(",");
            String cardName = cardProperties[0];
            if (numOfPlayers >= 2 && numOfPlayers <= 3) {
                if (cardProperties.length != 1) {
                    Card card = new Card();
                    drawDeck.addCard(card);
                }
            }
            numOfCardsUntilRequiredCountIsReached--;
        }
        if (numOfCardsUntilRequiredCountIsReached != 0) {
            throw new IllegalArgumentException("File does not match size of Party Pack deck");
        }
        return drawDeck;
    }
}

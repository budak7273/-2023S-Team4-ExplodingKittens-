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
            User user = new User(name);
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
    public DiscardDeck createDiscardDeck(){
        return null;
    }

    private DrawDeck createDrawDeckUsingScanner(Scanner cardInfoScanner) {
        DrawDeck drawDeck = new DrawDeck();
        String header = cardInfoScanner.next();
        int numOfCardsUntilRequiredCountIsReached = 113;

        while (cardInfoScanner.hasNext()) {
            String cardInfo = cardInfoScanner.next();
            String[] cardProperties = cardInfo.split(",");
            String cardType = cardProperties[0];
            boolean cardHasPawPrint = Boolean.parseBoolean(cardProperties[1]);

            // TODO: move this logic to an enum class
            String[] validTypes = new String[] {"Attack"};
            if (!Arrays.stream(validTypes).anyMatch(cardType::equals)) {
                throw new IllegalArgumentException("Invalid card type found in file");
            }

            // TODO: it's using attackCard() as dummy here. Fix it with necessary cards.
            if (numOfPlayers >= 2 && numOfPlayers <= 3) {
                if (cardHasPawPrint) {
                    Card card = new AttackCard();
                    drawDeck.addCard(card);
                }
            } else if (numOfPlayers >= 4 && numOfPlayers <= 7) {
                if (!cardHasPawPrint) {
                    Card card = new AttackCard();
                    drawDeck.addCard(card);
                }
            } else if (numOfPlayers >= 7 && numOfPlayers <= 10) {
                Card card = new AttackCard();
                drawDeck.addCard(card);
            }

            numOfCardsUntilRequiredCountIsReached--;
        }

        if (numOfCardsUntilRequiredCountIsReached != 0) {
            throw new IllegalArgumentException("File does not match size of Party Pack deck");
        }

        return drawDeck;
    }

}

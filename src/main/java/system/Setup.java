package system;

import datasource.CardCSVParser;
import java.io.File;
import java.util.*;

public class Setup {
    private int numOfPlayers;
    private final int minPlayers = 2;
    private final int maxPlayersWithPawprint = 3;
    private final int minPlayersWithoutPawprint = 4;
    private final int maxPlayersWithoutPawprint = 7;
    private final int maxPlayers = 10;
    private final int totalCardCount = 120;

    public Setup(final int playerCount) {
        this.numOfPlayers = playerCount;
    }

    public Queue<User> createUsers(final List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }

        if (names.size() < minPlayers
                || names.size() > maxPlayers) {
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

    public DrawDeck createDrawDeck(final File cardInfoFile) {
        DrawDeck drawDeck = new DrawDeck();
        CardCSVParser parser = new CardCSVParser(cardInfoFile);
        List<Card> cardList;
        if (numOfPlayers >= minPlayers
                && numOfPlayers <= maxPlayersWithPawprint) {
            cardList = parser.generateListOfCards(true, false);
        } else if (numOfPlayers >= minPlayersWithoutPawprint
                && numOfPlayers <= maxPlayersWithoutPawprint) {
            cardList = parser.generateListOfCards(false, true);
        } else {
            cardList = parser.generateListOfCards(true, true);
        }
        for (Card card : cardList) {
            drawDeck.addCard(card);
        }
        return drawDeck;
    }

    public DiscardDeck createDiscardDeck() {
        return new DiscardDeck();
    }

}

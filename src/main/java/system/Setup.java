package system;

import datasource.CardCSVParser;
import system.cards.DefuseCard;

import java.io.File;
import java.util.*;

public class Setup {
    private int numOfPlayers;

    private final int minPlayers = 2;
    private final int maxPlayers = 10;

    private final int maxCountWithPaw = 3;
    private final int minCountWithoutPaw = 4;
    private final int maxCountWithoutPaw = 7;

    private final int initialHandSize = 7;

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
        List<Card> cardList = generateCardList(cardInfoFile);
        DrawDeck drawDeck = generateDrawDeck(cardList);
        drawDeck.shuffle();
        return drawDeck;
    }

    private List<Card> generateCardList(final File cardInfoFile) {
        boolean countIsInPawOnlyBracket = numOfPlayers <= maxCountWithPaw;
        boolean countIsInNoPawOnlyBracket = numOfPlayers >= minCountWithoutPaw
                && numOfPlayers <= maxCountWithoutPaw;
        boolean countIsInAllBracket = !countIsInPawOnlyBracket
                && !countIsInNoPawOnlyBracket;

        boolean withPaw = countIsInPawOnlyBracket || countIsInAllBracket;
        boolean withNoPaw = countIsInNoPawOnlyBracket || countIsInAllBracket;

        CardCSVParser parser = new CardCSVParser(cardInfoFile);
        List<Card> cardList = parser.generateListOfCards(withPaw, withNoPaw);

        int numOfDefuseCardsToAdd;
        if (countIsInPawOnlyBracket) {
            numOfDefuseCardsToAdd = maxCountWithPaw - numOfPlayers;
        } else if (countIsInNoPawOnlyBracket) {
            numOfDefuseCardsToAdd = maxCountWithoutPaw - numOfPlayers;
        } else {
            numOfDefuseCardsToAdd = maxPlayers - numOfPlayers;
        }
        for (int i = 0; i < numOfDefuseCardsToAdd; i++) {
            cardList.add(new DefuseCard());
        }

        return cardList;
    }

    private DrawDeck generateDrawDeck(final List<Card> cardList) {
        DrawDeck drawDeck = new DrawDeck();
        for (Card card : cardList) {
            drawDeck.addCard(card);
        }
        return drawDeck;
    }

    public void dealHands(final Queue<User> playerQueue, final DrawDeck deck) {
        if (playerQueue.size() < 2 || playerQueue.size() > maxPlayers) {
            String msg = "Illegal number of players in queue.";
            throw new IllegalArgumentException(msg);
        }

        for (User user : playerQueue) {
            for (int i = 0; i < initialHandSize; i++) {
                deck.drawInitialCard(user);
            }
            user.addCard(new DefuseCard());
        }
    }

    public DiscardDeck createDiscardDeck() {
        return new DiscardDeck();
    }

}

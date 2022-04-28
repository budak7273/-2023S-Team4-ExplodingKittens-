package system;

import datasource.CardCSVParser;
import system.cards.DefuseCard;

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
        List<Card> cardList = generateCardList(cardInfoFile);
        DrawDeck drawDeck = generateDrawDeck(cardList);
        drawDeck.shuffle();
        return drawDeck;
    }

    private List<Card> generateCardList(File cardInfoFile) {
        boolean playerCountIsInPawOnlyBracket = numOfPlayers <= maxPlayersWithPawprint;
        boolean playerCountIsInNoPawOnlyBracket = numOfPlayers >= minPlayersWithoutPawprint
                && numOfPlayers <= maxPlayersWithoutPawprint;
        boolean playerCountIsInAllCardBracket = !playerCountIsInPawOnlyBracket
                && !playerCountIsInNoPawOnlyBracket;

        boolean includePaw = playerCountIsInPawOnlyBracket || playerCountIsInAllCardBracket;
        boolean includeNoPaw = playerCountIsInNoPawOnlyBracket || playerCountIsInAllCardBracket;

        CardCSVParser parser = new CardCSVParser(cardInfoFile);
        List<Card> cardList = parser.generateListOfCards(includePaw, includeNoPaw);

        int numOfDefuseCardsToAdd;
        if (playerCountIsInPawOnlyBracket) {
            numOfDefuseCardsToAdd = maxPlayersWithPawprint - numOfPlayers;
        } else if (playerCountIsInNoPawOnlyBracket) {
            numOfDefuseCardsToAdd = maxPlayersWithoutPawprint - numOfPlayers;
        } else {
            numOfDefuseCardsToAdd = maxPlayers - numOfPlayers;
        }
        for (int i = 0; i < numOfDefuseCardsToAdd; i++) {
            cardList.add(new DefuseCard());
        }

        return cardList;
    }

    private DrawDeck generateDrawDeck(List<Card> cardList) {
        DrawDeck drawDeck = new DrawDeck();
        for (Card card : cardList) {
            drawDeck.addCard(card);
        }
        return drawDeck;
    }

    public void dealHands(Queue<User> playerQueue, DrawDeck deck) {
        if (playerQueue.size() < 2 || playerQueue.size() > 10) {
            throw new IllegalArgumentException("Illegal number of players in queue");
        }

        for (User user : playerQueue) {
            int cardsToDraw = 7;
            for (int i = 0; i < cardsToDraw; i++) {
                deck.drawInitialCard(user);
            }
            user.addCard(new DefuseCard());
        }
    }

    public DiscardDeck createDiscardDeck() {
        return new DiscardDeck();
    }

}

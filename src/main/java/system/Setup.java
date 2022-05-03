package system;

import datasource.CardCSVParser;
import datasource.Messages;
import system.cards.DefuseCard;

import java.io.File;
import java.util.*;

public class Setup {
    private int numOfPlayers;

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;


    private static final int MAX_COUNT_WITH_PAW = 3;
    private static final int MIN_COUNT_WITHOUT_PAW = 4;
    private static final int MAX_COUNT_WITHOUT_PAW = 7;

    private static final int INITIAL_HAND_SIZE = 7;

    public Setup(final int playerCount) {
        this.numOfPlayers = playerCount;
    }

    public Queue<User> createUsers(final List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }

        if (names.size() < MIN_PLAYERS
                || names.size() > MAX_PLAYERS) {
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
        boolean countIsInPawOnlyBracket = numOfPlayers <= MAX_COUNT_WITH_PAW;
        boolean countIsInNoPawOnlyBracket =
                numOfPlayers >= MIN_COUNT_WITHOUT_PAW
                && numOfPlayers <= MAX_COUNT_WITHOUT_PAW;
        boolean countIsInAllBracket = !countIsInPawOnlyBracket
                && !countIsInNoPawOnlyBracket;

        boolean withPaw = countIsInPawOnlyBracket || countIsInAllBracket;
        boolean withNoPaw = countIsInNoPawOnlyBracket || countIsInAllBracket;

        CardCSVParser parser = new CardCSVParser(cardInfoFile);
        List<Card> cardList = parser.generateListOfCards(withPaw, withNoPaw);

        int numOfDefuseCardsToAdd;
        if (countIsInPawOnlyBracket) {
            numOfDefuseCardsToAdd = MAX_COUNT_WITH_PAW - numOfPlayers;
        } else if (countIsInNoPawOnlyBracket) {
            numOfDefuseCardsToAdd = MAX_COUNT_WITHOUT_PAW - numOfPlayers;
        } else {
            numOfDefuseCardsToAdd = MAX_PLAYERS - numOfPlayers;
        }
        for (int i = 0; i < numOfDefuseCardsToAdd; i++) {
            cardList.add(new DefuseCard());
        }

        return cardList;
    }

    private DrawDeck generateDrawDeck(final List<Card> cardList) {
        return new DrawDeck(cardList);
    }

    public void dealHands(final Queue<User> playerQueue, final DrawDeck deck) {

        if (playerQueue.size() < 2 || playerQueue.size() > MAX_PLAYERS) {

            throw new IllegalArgumentException(
                    Messages.ILLEGAL_PLAYERS.toString());

        }

        for (User user : playerQueue) {
            for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
                deck.drawInitialCard(user);
            }
            user.addCard(new DefuseCard());
        }
    }

    public DiscardDeck createDiscardDeck() {
        return new DiscardDeck();
    }

}

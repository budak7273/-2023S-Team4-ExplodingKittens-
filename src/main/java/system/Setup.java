package system;

import datasource.CardCSVParser;
import datasource.CardType;
import datasource.I18n;

import javax.swing.ImageIcon;
import java.io.File;
import java.util.*;

public class Setup {
    private final Random random;
    private int numOfPlayers;

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;
    private static final int MAX_COUNT_WITH_PAW = 3;
    private static final int MIN_COUNT_WITHOUT_PAW = 4;
    private static final int MAX_COUNT_WITHOUT_PAW = 7;

    private static final int INITIAL_HAND_SIZE = 7;

    // For tests that do not care about specific random seeds
    public Setup(int playerCount) {
        this(playerCount, new Random());
    }

    public Setup(int playerCount, Random inputRandom) {
        this.numOfPlayers = playerCount;
        this.random = inputRandom;
    }

    public Queue<User> createUsers(List<String> names) {
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

    public DrawDeck createDrawDeck(File cardInfoFile) {
        List<Card> cardList = generateCardList(cardInfoFile);
        DrawDeck drawDeck = generateDrawDeck(cardList);
        drawDeck.shuffle();
        return drawDeck;
    }

    private List<Card> generateCardList(File cardInfoFile) {
        boolean smallGame = numOfPlayers <= MAX_COUNT_WITH_PAW;
        boolean mediumGame = numOfPlayers >= MIN_COUNT_WITHOUT_PAW
                && numOfPlayers <= MAX_COUNT_WITHOUT_PAW;

        boolean includePaw;
        boolean includePawless;
        int numOfDefuseCardsToAdd;

        if (smallGame) {
            includePaw = true;
            includePawless = false;
            numOfDefuseCardsToAdd = MAX_COUNT_WITH_PAW - numOfPlayers;
        } else if (mediumGame) {
            includePaw = false;
            includePawless = true;
            numOfDefuseCardsToAdd = MAX_COUNT_WITHOUT_PAW - numOfPlayers;
        } else {
            includePaw = true;
            includePawless = true;
            numOfDefuseCardsToAdd = MAX_PLAYERS - numOfPlayers;
        }
        CardCSVParser parser = new CardCSVParser(cardInfoFile);
        List<Card> cardList = parser.generateListOfCardsWithVerification(includePaw, includePawless);

        for (int i = 0; i < numOfDefuseCardsToAdd; i++) {
            cardList.add(new Card(CardType.DEFUSE, null));
        }
        return cardList;
    }

    private DrawDeck generateDrawDeck(List<Card> cardList) {
        return new DrawDeck(cardList, random);
    }

    public void dealHands(Queue<User> playerQueue, DrawDeck deck) {
        if (playerQueue.size() < 2 || playerQueue.size() > MAX_PLAYERS) {
            String msg = I18n.getMessage("IllegalPlayersMessage") + ": " + playerQueue.size();
            throw new IllegalArgumentException(msg);
        }

        for (User user : playerQueue) {
            for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
                deck.drawCard(user);
            }
            user.addCard(new Card(CardType.DEFUSE, new ImageIcon("src/main/resources/images/defuse.png")));
        }
    }

    public void shuffleExplodingKittensInDeck(DrawDeck deck) {
        for (int i = 0; i < numOfPlayers - 1; i++) {
            deck.addCardToTop(new Card(CardType.EXPLODING_KITTEN, null));
        }
        deck.shuffle();
    }
}

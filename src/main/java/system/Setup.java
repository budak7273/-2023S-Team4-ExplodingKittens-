package system;

import datasource.CardCSVParser;
import datasource.CardType;
import datasource.I18n;
import datasource.ResourceHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Setup {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;
    private static final int MAX_COUNT_WITH_PAW = 3;
    private static final int MIN_COUNT_WITHOUT_PAW = 4;
    private static final int MAX_COUNT_WITHOUT_PAW = 7;
    private static final int INITIAL_HAND_SIZE = 7;
    private final Random random;
    private int numOfPlayers;

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

    public DrawDeck createDrawDeck(InputStream stream) {
        List<Card> cardList = generateCardList(stream);
        DrawDeck drawDeck = generateDrawDeck(cardList);
        drawDeck.shuffle();
        return drawDeck;
    }

    public DrawDeck createDrawDeck(File cardInfoFile) {
        try {
            return createDrawDeck(new FileInputStream(cardInfoFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Failed to set up input stream for cards file", e);
        }
    }

    private List<Card> generateCardList(InputStream cardInputStream) {
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
        CardCSVParser parser = new CardCSVParser(cardInputStream);
        List<Card> cardList = parser.generateListOfCardsWithVerification(includePaw, includePawless);

        for (int i = 0; i < numOfDefuseCardsToAdd; i++) {
            cardList.add(new Card(CardType.DEFUSE, ResourceHelper.getAsImageIcon("/images/defuse.png")));
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
            user.addCard(new Card(CardType.DEFUSE, ResourceHelper.getAsImageIcon("/images/defuse.png")));
        }
    }

    public void shuffleExplodingKittensInDeck(DrawDeck deck) {
        for (int i = 0; i < numOfPlayers - 1; i++) {
            deck.addCardToTop(new Card(CardType.EXPLODING_KITTEN, null));
        }
        deck.shuffle();
    }
}

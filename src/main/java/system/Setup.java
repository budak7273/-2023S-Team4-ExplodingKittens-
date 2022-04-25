package system;

import java.io.File;
import java.io.FileNotFoundException;
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

        if (names.size() < minPlayers || names.size() > maxPlayers) {
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
        try {
            Scanner cardInfoScanner = new Scanner(cardInfoFile);
            drawDeck = createDrawDeckUsingScanner(cardInfoScanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return drawDeck;
    }

    public DiscardDeck createDiscardDeck() {
        return new DiscardDeck();
    }

    private DrawDeck createDrawDeckUsingScanner(final Scanner cardInfoScanner) {
        DrawDeck drawDeck = new DrawDeck();
        String header = cardInfoScanner.nextLine();
        int numOfCardsUntilRequiredCountIsReached = totalCardCount;

        int line = 0;
        while (cardInfoScanner.hasNextLine()) {
            String cardInfo = cardInfoScanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            String cardType = cardProperties[0];
            boolean cardHasPawPrint = Boolean.parseBoolean(cardProperties[1]);

            // TODO: move this logic to an enum class
            String[] validTypes = new String[] {"Attack", "Exploding Kitten",
                    "Defuse", "Skip", "Favor", "Shuffle",
            "Beard Cat", "Tacocat", "Hairy Potato Cat", "Rainbow-Ralphing Cat",
                    "Cattermelon", "Feral Cat",
            "Draw From The Bottom", "Nope", "Alter The Future",
                    "Targeted Attack", "See The Future"};
            if (!Arrays.stream(validTypes).anyMatch(cardType::equals)) {
                throw new IllegalArgumentException("Invalid card type "
                        + cardType + " found in file");
            }

            // TODO: it's using attackCard() as dummy here.
            //  Fix it with necessary cards.
            if (numOfPlayers >= minPlayers
                    && numOfPlayers <= maxPlayersWithPawprint) {
                if (cardHasPawPrint) {
                    Card card = new AttackCard();
                    drawDeck.addCard(card);
                }
            } else if (numOfPlayers >= minPlayersWithoutPawprint
                    && numOfPlayers <= maxPlayersWithoutPawprint) {
                if (!cardHasPawPrint) {
                    Card card = new AttackCard();
                    drawDeck.addCard(card);
                }
            } else if (numOfPlayers >= maxPlayersWithPawprint
                    && numOfPlayers <= maxPlayers) {
                Card card = new AttackCard();
                drawDeck.addCard(card);
            }

            numOfCardsUntilRequiredCountIsReached--;
        }

        if (numOfCardsUntilRequiredCountIsReached != 0) {
            throw new IllegalArgumentException("File "
                    + "does not match size of Party Pack deck");
        }

        return drawDeck;
    }
}

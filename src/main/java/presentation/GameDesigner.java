package presentation;

import datasource.I18n;
import datasource.ResourceHelper;
import system.DrawDeck;
import system.GameManager;
import system.GameState;
import system.Setup;
import system.User;

import javax.swing.JFrame;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class GameDesigner {
    private static final int MAX_PLAYER_COUNT = 10;
    private static final int MIN_PLAYER_COUNT = 2;
    private static final boolean DEBUG_AUTO_PLAYER_ENTRY_MODE = true;
    private Queue<User> users;
    private GameWindow gameWindow;
    private JFrame gameFrame;

    public GameDesigner(JFrame frame) {
        this(new ArrayDeque<>(), frame);
    }

    public GameDesigner(Queue<User> usersQueue, JFrame frame) {
        this.gameFrame = frame;
        this.users = usersQueue;
    }

    /**
     * createGame takes in no parameters.
     * Is tasked with initializing the current game.
     */
    public final void createGame() throws InvalidPlayerCountException {
        List<String> usernames = readUserInfo();
        if (usernames.size() == 1) {
            throw new InvalidPlayerCountException(
                    I18n.getMessage("NotEnoughPlayersMessage"));
        }

        initializeGameState(usernames);
    }

    public void initializeGameState(final List<String> usernames) {
        Setup setup = new Setup(usernames.size(), new Random());
        users = setup.createUsers(usernames);
        DrawDeck drawDeck = setup.createDrawDeck(ResourceHelper.getAsStream("/data/cards.csv"));
        setup.dealHands(users, drawDeck);
        setup.shuffleExplodingKittensInDeck(drawDeck);

        gameWindow = new GameWindow(gameFrame, false);
        final GameState gameState = new GameState(users, drawDeck);
        final GameManager gameManager = new GameManager(gameState, gameWindow);
        gameWindow.setGameManager(gameManager);
        gameWindow.updateUI();
    }

    /**
     * These methods should only be used for Integration Testing
     */
    public void initializeGameState(Random random) {
        Setup setup = new Setup(users.size(), random);
        DrawDeck drawDeck = setup.createDrawDeck(ResourceHelper.getAsStream("/data/cards.csv"));
        setup.dealHands(this.users, drawDeck);
        gameWindow = new GameWindow(gameFrame, true);
        GameState gameState = new GameState(this.users, drawDeck);
        GameManager gameManager = new GameManager(gameState, gameWindow);
        gameWindow.setGameManager(gameManager);
        gameWindow.updateUI();
    }

    public GameWindow getGameWindow() {
        return this.gameWindow;
    }

    /**
     * readUserInfo takes in no parameters.
     * Asks the User for their information.
     *
     * @return a list of Strings that represent the current User's name
     */
    public static List<String> readUserInfo() {
        List<String> userNameList;
        Scanner scanner = new Scanner(System.in, "UTF-8");

        setupLanguage(scanner);

        if (DEBUG_AUTO_PLAYER_ENTRY_MODE) {
            String[] names = {"one", "two"};
            userNameList = Arrays.asList(names);
        } else {
            userNameList = setupPlayerUsernames(scanner);
        }

        displayPlayerList(userNameList);

        scanner.close();
        return userNameList;
    }

    private static void setupLanguage(Scanner scanner) {
        System.out.println(I18n.getMessage("ChooseLanguageMessage"));
        for (I18n language : I18n.values()) {
            System.out.println(language.localeSummaryString());
        }
        String languageSelection = DEBUG_AUTO_PLAYER_ENTRY_MODE ? "e" : scanner.nextLine().toLowerCase();
        String selected = I18n.switchLanguage(languageSelection);
        System.out.println(I18n.getMessage("LanguageSelected") + " " + selected);
    }

    private static List<String> setupPlayerUsernames(Scanner scanner) {
        List<String> userNameList = new ArrayList<>();
        enterPlayerNames(scanner, userNameList);
        return userNameList;
    }

    private static void enterPlayerNames(Scanner scanner, List<String> userNameList) {
        boolean addMorePlayers = true;
        while (addMorePlayers) {
            int nextPlayerCount = userNameList.size() + 1;
            System.out.println(I18n.getMessage("EnterPlayerMessage")
                               + nextPlayerCount + I18n
                                       .getMessage("PlayerUsernameMessage"));
            userNameList.add(collectUsername(scanner, userNameList));
            int upcomingCount = userNameList.size() + 1;
            if (upcomingCount > MAX_PLAYER_COUNT) {
                addMorePlayers = false;
            } else if (nextPlayerCount >= MIN_PLAYER_COUNT) {
                System.out.println(I18n.getMessage("AddAnotherPlayerMessage"));

                String response = scanner.nextLine().toLowerCase();
                addMorePlayers = response.equals("y") || response.equals("j");
            }
        }
    }

    private static String collectUsername(Scanner scanner, List<String> userNameList) {
        String selectedUsername = null;
        do {
            String input = scanner.nextLine();
            if (userNameList.contains(input)) {
                System.out.println(I18n.getMessage("DuplicatedUserName"));
            } else {
                System.out.println(input + I18n.getMessage("PlayerAddedToGameMessage"));
                selectedUsername = input;
            }
        } while (selectedUsername == null);
        return selectedUsername;
    }

    private static void displayPlayerList(List<String> userNameList) {
        System.out.println(I18n.getMessage("StartGameMessage"));
        for (String userName : userNameList) {
            System.out.println(userName);
        }
    }
}

package presentation;

import datasource.Messages;
import system.*;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class GameDesigner {
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
                    Messages.getMessage(Messages.NOT_ENOUGH_PLAYERS));
        }

        initializeGameState(usernames);
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

        userNameList = setupPlayerUsernames(scanner);

        displayPlayerList(userNameList);

        scanner.close();
        return userNameList;
    }

    public void initializeGameState(final List<String> usernames) {
        Setup setup = new Setup(usernames.size(), new Random());
        users = setup.createUsers(usernames);
        String path = "src/main/resources/cards.csv";
        DrawDeck drawDeck = setup.createDrawDeck(new File(path));
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
        String path = "src/main/resources/cards.csv";
        DrawDeck drawDeck = setup.createDrawDeck(new File(path));
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

    private static void setupLanguage(Scanner scanner) {
        System.out.print(Messages.getMessage(Messages.CHOOSE_LANGUAGE));
        String languageSelection = scanner.nextLine().toLowerCase();
        Messages.switchLanguage(languageSelection);
    }

    private static List<String> setupPlayerUsernames(Scanner scanner) {
        List<String> userNameList = new ArrayList<>();

        System.out.println(Messages.getMessage(Messages.ENTER_PLAYER_1_NAME));

        enterPlayerNames(scanner, userNameList);
        return userNameList;
    }

    private static void enterPlayerNames(Scanner scanner, List<String> userNameList) {
        int nextPlayerCount = 2;
        final int tooManyPlayers = 11;
        while (scanner.hasNextLine()) {
            if (enterAUsername(scanner, userNameList)) {
                continue;
            }

            if (nextPlayerCount >= tooManyPlayers) {
                break;
            }
            System.out.println(Messages.getMessage(
                    Messages.ADD_ANOTHER_PLAYER));

            String response = scanner.nextLine().toLowerCase();
            boolean addAnotherPlayer = (response.equals("y")
                    || response.equals("j"));
            if (!addAnotherPlayer) {
                break;
            }

            System.out.println(Messages.getMessage(Messages.ENTER_PLAYER)
                               + nextPlayerCount + Messages
                    .getMessage(Messages.PLAYER_USERNAME));
            nextPlayerCount++;
        }
    }

    private static boolean enterAUsername(Scanner scanner, List<String> userNameList) {
        String username = scanner.nextLine();
        if (userNameList.contains(username)) {
            System.out.println(Messages.getMessage(
                    Messages.DUPLICATED_USERNAME));
            return true;
        }

        userNameList.add(username);
        System.out.println(username + Messages
                .getMessage(Messages.PLAYER_ADDED_TO_GAME));
        return false;
    }

    private static void displayPlayerList(List<String> userNameList) {
        System.out.println(Messages.getMessage(Messages.START_GAME));
        for (String userName : userNameList) {
            System.out.println(userName);
        }
    }
}

package presentation;

import datasource.Messages;
import system.*;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class GameDesigner {
    private Queue<User> users;
    private GamePlayer gamePlayer;
    private JFrame gameFrame;

    public GameDesigner(JFrame frame) {
        this.gameFrame = frame;
        this.users = new ArrayDeque<>();
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
        Setup setup = new Setup(usernames.size());
        users = setup.createUsers(usernames);
        String path = "src/main/resources/cards.csv";
        DrawDeck drawDeck = setup.createDrawDeck(new File(path));
        setup.dealHands(users, drawDeck);
        setup.shuffleExplodingKittensInDeck(drawDeck);

        gamePlayer = new GamePlayer(gameFrame);
        final GameState gameState = new GameState(users,
                gamePlayer, drawDeck);
        gamePlayer.setGameState(gameState);
        AudioPlayer.playMusicOnStartup();
        gamePlayer.updateUI();
    }

    /**
     * These methods should only be used for Integration Testing
     *
     * @return
     */
    public void initializeGameState() {
        Setup setup = new Setup(users.size());
        String path = "src/main/resources/cards.csv";
        DrawDeck drawDeck = setup.createDrawDeck(new File(path));
        setup.dealHands(this.users, drawDeck);
        gamePlayer = new GamePlayer(gameFrame);
        GameState gameState = new GameState(this.users, gamePlayer, drawDeck);
        gamePlayer.setGameState(gameState);
        gamePlayer.updateUI();
    }

    public GamePlayer getGamePlayer() {
        return this.gamePlayer;
    }
    private static void setupLanguage(Scanner scanner) {
        System.out.print(Messages.getMessage(Messages.CHOOSE_LANGUAGE));
        String languageSelection = scanner.nextLine().toLowerCase();
        Messages.switchLanguage(languageSelection);
    }
    private static List<String> setupPlayerUsernames(Scanner scanner) {
        List<String> userNameList = new ArrayList<>();
        int nextPlayerCount = 2;
        final int tooManyPlayers = 11;

        System.out.println(Messages.getMessage(Messages.ENTER_PLAYER_1_NAME));

        while (scanner.hasNextLine()) {
            String username = scanner.nextLine();
            if (userNameList.contains(username)) {
                System.out.println(Messages.getMessage(
                        Messages.DUPLICATED_USERNAME));
                continue;
            }

            userNameList.add(username);
            System.out.println(username + Messages
                    .getMessage(Messages.PLAYER_ADDED_TO_GAME));

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
        return userNameList;
    }
    private static void displayPlayerList(List<String> userNameList) {
        System.out.println(Messages.getMessage(Messages.START_GAME));
        for (String userName : userNameList) {
            System.out.println(userName);
        }
    }
}

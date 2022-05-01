package presentation;

import system.*;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class GameDesigner {

    /**This is the internal storage of GameDesigner.
     * which allows the user to Create Prestentation.*/
    public GamePlayer gamePlayer;
    /**This is the Queue of all Player's in the current game.*/
    private Queue<User> users = new ArrayDeque<>();
    /**This is the drawDeck in the current game.*/
    private DrawDeck drawDeck;
    /**This is the discardDeck in the current game.*/
    private DiscardDeck discardDeck;
    /**This is the current game's gameState.*/
    private GameState gameState;
    /**This is the current game's Frame that is being drawn on.*/
    private JFrame gameFrame;

    public GameDesigner(GamePlayer gamePlayer){
        this.gamePlayer = gamePlayer;
    }

    /** createGame takes in no parameters.
     *  Is tasked with initializing the current game.*/
    public final void createGame() throws InvalidPlayerCountException {
        List<String> usernames = readUserInfo();
        if (usernames.size() == 1) {
            throw new InvalidPlayerCountException("ERROR: "
                    + "Must have at least 2 players!");
        }

        initializeGameState(usernames);
        initializeGameView();
    }

    /**
     * readUserInfo takes in no parameters.
     *      Asks the User for their information.
     * @return a list of Strings that represent the current User's name
     */
    public static List<String> readUserInfo() {
        List<String> userNameList = new ArrayList<>();
        int nextPlayerCount = 2;
        final int tooManyPlayers = 11;
        System.out.println("Please enter player 1's username!");
        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (scanner.hasNext()) {
            String username = scanner.next();
            userNameList.add(username);
            System.out.println(username + " has been added to the game!");

            if (nextPlayerCount < tooManyPlayers) {
                System.out.println("Would you like "
                        + "to add another player? (y/n)");
            } else {
                break;
            }

            String response = scanner.next().toLowerCase();
            boolean addAnotherPlayer = (response.equals("y"));
            if (addAnotherPlayer) {
                System.out.println("Enter player "
                        + nextPlayerCount + "'s username!");
                nextPlayerCount++;
            } else {
                break;
            }
        }

        System.out.println("Starting Exploding Kittens game for players: ");
        for (String userName: userNameList) {
            System.out.println(userName);
        }
        scanner.close();
        return userNameList;
    }

    private void initializeGameState(final List<String> usernames) {
        Setup setup = new Setup(usernames.size());
        this.users = setup.createUsers(usernames);
        String path = "src/main/resources/cards.csv";
        this.drawDeck = setup.createDrawDeck(new File(path));
        this.discardDeck = setup.createDiscardDeck();

//        this.gameState = new GameState(this.users);
        setup.dealHands(this.users, this.drawDeck);
    }

    private void initializeGameView() {
        this.gameFrame = new JFrame();
        gamePlayer.buildGameView();
    }
}

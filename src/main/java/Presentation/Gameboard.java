package Presentation;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

import System.*;

import javax.swing.*;

public class Gameboard {
    private Queue<User> users = new ArrayDeque<>();
    private DrawDeck drawDeck = new DrawDeck();
    private DiscardDeck discardDeck = new DiscardDeck();
    private GameState gameState;
    Scanner scanner = new Scanner(System.in);

    public void createGame() throws InvalidPlayerCountException {
        List<String> usernames = readUserInfo();
        int playerCount = usernames.size();
        if (playerCount == 1) {
            throw new InvalidPlayerCountException("ERROR: Must have at least 2 players!");
        }

        Setup setup = new Setup(playerCount);
        this.users = setup.createUsers(usernames);
        String path = "src/main/resources/cards.csv";
        this.drawDeck = setup.createDrawDeck(new File(path));
        this.discardDeck = setup.createDiscardDeck();

        this.gameState = new GameState(this.users);

        initializeGameView();
    }

    public List<String> readUserInfo(){
        List<String> userNameList = new ArrayList<>();
        int nextPlayerCount = 2;

        System.out.println("Please enter player 1's username!");
        while (scanner.hasNext()) {
            String username = scanner.next();
            userNameList.add(username);
            System.out.println(username + " has been added to the game!");

            if (nextPlayerCount < 11) {
                System.out.println("Would you like to add another player? (y/n)");
            } else {
                break;
            }

            String response = scanner.next().toLowerCase();
            boolean addAnotherPlayer = (response.equals("y"));
            if (addAnotherPlayer) {
                System.out.println("Enter player " + nextPlayerCount + "'s username!");
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

    private void initializeGameView() {
        JFrame gameFrame = new JFrame();
        JPanel userDisplayPanel = generateUserDisplayPanel();
        JPanel tableAreaDisplayPanel = generateTableAreaDisplayPanel();
        JPanel playerDeckDisplayPanel = generatePlayerDeckDisplayPanel();

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(userDisplayPanel, BorderLayout.NORTH);
        gameFrame.add(tableAreaDisplayPanel, BorderLayout.CENTER);
        gameFrame.add(playerDeckDisplayPanel, BorderLayout.SOUTH);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }

    private JPanel generateUserDisplayPanel() {
        JPanel userDisplayPanel = new JPanel();
        userDisplayPanel.add(new JLabel("Placeholder for user display."));
        return userDisplayPanel;
    }

    private JPanel generateTableAreaDisplayPanel() {
        JPanel tableAreaDisplayPanel = new JPanel();
        tableAreaDisplayPanel.add(new JLabel(
                "Placeholder for displaying the table area. (Depicting the draw deck, discard deck, etc.)"));
        return tableAreaDisplayPanel;
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        JPanel playerDeckDisplayPanel = new JPanel();
        playerDeckDisplayPanel.setLayout(new BorderLayout());

        JLabel playerNameLabel = new JLabel("It is your turn, " + gameState.getUsernameForCurrentTurn());
        playerDeckDisplayPanel.add(playerNameLabel, BorderLayout.NORTH);

        for (Card card : gameState.getDeckForCurrentTurn()) {
            JLabel cardNameLabel = new JLabel(card.getName());
            playerDeckDisplayPanel.add(cardNameLabel, BorderLayout.CENTER);
        }

        return playerDeckDisplayPanel;
    }

    public Queue<User> getUsers() {
        return this.users;
    }

    public DrawDeck getDrawDeck() {
        return this.drawDeck;
    }

    public DiscardDeck getDiscardDeck() {
        return this.discardDeck;
    }


}

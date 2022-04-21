package Presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JFrame gameFrame;

    public void createGame() throws InvalidPlayerCountException {
        List<String> usernames = readUserInfo();
        if (usernames.size() == 1) {
            throw new InvalidPlayerCountException("ERROR: Must have at least 2 players!");
        }

        initializeGameState(usernames);
        initializeGameView();
    }

    public List<String> readUserInfo(){
        List<String> userNameList = new ArrayList<>();
        int nextPlayerCount = 2;

        System.out.println("Please enter player 1's username!");
        Scanner scanner = new Scanner(System.in);
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

    private void initializeGameState(List<String> usernames) {
        Setup setup = new Setup(usernames.size());
        this.users = setup.createUsers(usernames);
        String path = "src/main/resources/cards.csv";
        this.drawDeck = setup.createDrawDeck(new File(path));
        this.discardDeck = setup.createDiscardDeck();

        this.gameState = new GameState(this.users, this);
    }

    private void initializeGameView() {
        this.gameFrame = new JFrame();
        buildGameView();
    }

    private void buildGameView() {
        gameFrame.getContentPane().removeAll();
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
        JButton drawCardButton = new JButton("Draw Card");
        drawCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawDeck.drawCard(gameState.getUserForCurrentTurn());
                gameState.transitionToNextTurn();
            }
        });

        tableAreaDisplayPanel.add(drawCardButton);
        return tableAreaDisplayPanel;
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        JPanel playerDeckDisplayPanel = new JPanel();
        playerDeckDisplayPanel.setLayout(new BorderLayout());

        JLabel playerNameLabel = new JLabel("It is your turn, " + gameState.getUsernameForCurrentTurn());
        playerDeckDisplayPanel.add(playerNameLabel, BorderLayout.NORTH);

        JPanel handDisplayPanel = new JPanel();
        handDisplayPanel.setLayout(new BoxLayout(handDisplayPanel, BoxLayout.Y_AXIS));
        for (Card card : gameState.getDeckForCurrentTurn()) {
            JLabel cardNameLabel = new JLabel(card.getName());
            handDisplayPanel.add(cardNameLabel);
        }

        playerDeckDisplayPanel.add(handDisplayPanel, BorderLayout.CENTER);
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

    public void updateUI() {
        buildGameView();
    }
}

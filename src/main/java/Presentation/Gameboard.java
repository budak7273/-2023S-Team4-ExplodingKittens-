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
    private DrawDeck drawDeck;
    private DiscardDeck discardDeck;
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
        gameFrame.setSize(1000,500);
        gameFrame.setVisible(true);
    }

    private JPanel generateUserDisplayPanel() {
        JPanel userDisplayPanel = new JPanel();
        for(User user: this.users){
            if(user != this.gameState.getUserForCurrentTurn()){
                JPanel otherPlayer = createCardImage(user.getName(), user.getHand().size() +"");
                userDisplayPanel.add(otherPlayer);
            }
        }
        return userDisplayPanel;
    }

    private JPanel generateTableAreaDisplayPanel() {
        JPanel tableAreaDisplayPanel = new JPanel();

        JButton deckButton = createDeckImage(drawDeck.getDeckSize() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawDeck.drawCard(gameState.getUserForCurrentTurn());
                gameState.transitionToNextTurn();
            }
        });
        JPanel discardPile = createCardImage("Top Card", this.discardDeck.getDeckSize() + "");
        tableAreaDisplayPanel.add(discardPile, BorderLayout.SOUTH);
        tableAreaDisplayPanel.add(deckButton, BorderLayout.NORTH);
        return tableAreaDisplayPanel;
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        JPanel playerDeckDisplayPanel = new JPanel();
        playerDeckDisplayPanel.setLayout(new BorderLayout());

        JLabel playerNameLabel = new JLabel("It is your turn, " + gameState.getUsernameForCurrentTurn());
        playerDeckDisplayPanel.add(playerNameLabel, BorderLayout.NORTH);

        JPanel handDisplayPanel = new JPanel();
        handDisplayPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        for (Card card : gameState.getDeckForCurrentTurn()) {
            JPanel cardLayout = createCardImage(card.getName(), "Effect");
            handDisplayPanel.add(cardLayout);
        }

        playerDeckDisplayPanel.add(handDisplayPanel, BorderLayout.CENTER);
        return playerDeckDisplayPanel;
    }

    private JButton createDeckImage(String desc){
        JButton deckImage = new JButton("<html><center>Draw Deck<br>"+ desc + "</center></html>");
        deckImage.setBackground(Color.GREEN);
//        deckImage.add(new JLabel("Draw Deck"), BorderLayout.NORTH);
//        deckImage.add(new JLabel(desc), BorderLayout.SOUTH);
        return deckImage;
    }

    private JPanel createCardImage(String name, String desc){
        JPanel cardImage = new JPanel();
        cardImage.setLayout(new GridLayout(0, 1));
        cardImage.setPreferredSize(new Dimension(55, 30));
        cardImage.setBackground(Color.magenta);
        cardImage.add(new JLabel(name), SwingConstants.CENTER);
        cardImage.add(new JLabel(desc), SwingConstants.CENTER);
        return cardImage;
    }

    public Queue<User> getUsers() {
        return this.users;
    }

    public DrawDeck getDrawDeck() {
        return this.drawDeck;
    }

    public void updateUI() {
        buildGameView();
    }
}

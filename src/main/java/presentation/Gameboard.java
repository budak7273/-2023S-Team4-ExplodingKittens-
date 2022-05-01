package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import system.User;
import system.DrawDeck;
import system.DiscardDeck;
import system.GameState;
import system.Setup;
import system.Card;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Gameboard {
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

        this.gameState = new GameState(this.users, this);
        setup.dealHands(this.users, this.drawDeck);
    }

    private void initializeGameView() {
        this.gameFrame = new JFrame();
        buildGameView();
    }

    private void buildGameView() {
        final int frameWidth = 1000;
        final int frameHeight = 500;
        gameFrame.getContentPane().removeAll();
        JPanel userDisplayPanel = generateUserDisplayPanel();
        JPanel tableAreaDisplayPanel = generateTableAreaDisplayPanel();
        JPanel playerDeckDisplayPanel = generatePlayerDeckDisplayPanel();

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(userDisplayPanel, BorderLayout.NORTH);
        gameFrame.add(tableAreaDisplayPanel, BorderLayout.CENTER);
        gameFrame.add(playerDeckDisplayPanel, BorderLayout.SOUTH);
        gameFrame.pack();
        gameFrame.setSize(frameWidth, frameHeight);
        gameFrame.setVisible(true);
    }

    private JPanel generateUserDisplayPanel() {
        JPanel userDisplayPanel = new JPanel();
        for (User user: this.users) {
            if (user != this.gameState.getUserForCurrentTurn()) {
                JPanel otherPlayer =
                        createCardImage(user.getName(),
                                user.getHand().size() + "");
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
            public void actionPerformed(final ActionEvent e) {
                drawDeck.drawCard(gameState.getUserForCurrentTurn());
                gameState.transitionToNextTurn();
            }
        });
        JPanel discardPile = createCardImage("Top Card",
                this.discardDeck.getDeckSize() + "");
        tableAreaDisplayPanel.add(discardPile, BorderLayout.SOUTH);
        tableAreaDisplayPanel.add(deckButton, BorderLayout.NORTH);
        return tableAreaDisplayPanel;
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        JPanel playerDeckDisplayPanel = new JPanel();
        playerDeckDisplayPanel.setLayout(new BorderLayout());
        JLabel playerNameLabel =
                new JLabel("It is your turn, "
                        + gameState.getUserForCurrentTurn().getName());
        playerDeckDisplayPanel.add(playerNameLabel, BorderLayout.NORTH);

        JPanel handDisplayPanel = new JPanel();
        handDisplayPanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        for (Card card : gameState.getUserForCurrentTurn().getHand()) {
            JPanel cardLayout = createCardImage(card.getName(), "");
            handDisplayPanel.add(cardLayout);
        }
        playerDeckDisplayPanel.add(handDisplayPanel, BorderLayout.CENTER);
        return playerDeckDisplayPanel;
    }

    private JButton createDeckImage(final String desc) {
        JButton deckImage = new JButton("<html><center>Draw Deck<br>"
                + desc + "</center></html>");
        deckImage.setBackground(Color.GREEN);
        return deckImage;
    }

    private JPanel createCardImage(final String name, final String desc) {
        final int cardWidth = 55;
        final int cardHeight = 80;
        JPanel cardImage = new JPanel();
        cardImage.setLayout(new GridLayout(0, 1));
        cardImage.setPreferredSize(new Dimension(cardWidth, cardHeight));
        cardImage.setBackground(Color.magenta);
        JLabel cardDetails = new JLabel();
        cardDetails.setText("<html><overflow='hidden'>"
                + name + "<br>" + desc + "</html>");
        cardImage.add(cardDetails);
        return cardImage;
    }

    /**
     * updateUI changes the GUI of the current game when it is called.
     */
    public final void updateUI() {
        buildGameView();
    }
}

package presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;

import datasource.Messages;
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

    public Gameboard() {
        this.gameFrame = new JFrame();
    }
    public Gameboard(Queue<User> usersQueue){
        this.users = usersQueue;
    }

    /** createGame takes in no parameters.
     *  Is tasked with initializing the current game.*/
    public void createGame() throws InvalidPlayerCountException {
        List<String> usernames = readUserInfo();
        if (usernames.size() == 1) {
            String msg = Messages.getMessage(Messages.NOT_ENOUGH_PLAYERS);
            throw new InvalidPlayerCountException(msg);
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
        int tooManyPlayers = 11;
        System.out.print(Messages.getMessage(Messages.CHOOSE_LANGUAGE));
        Scanner scanner = new Scanner(System.in, "UTF-8");
        String languageSelection = scanner.next().toLowerCase();
        boolean useGermanLanguage = (languageSelection.equals("g"));

        if(useGermanLanguage){
            Messages.switchLanguageToGerman();
        }
        System.out.println(Messages
                .getMessage(Messages.ENTER_PLAYER_1_NAME));


        while (scanner.hasNext()) {
            String username = scanner.next();
            userNameList.add(username);
            System.out.println(username + Messages
                    .getMessage(Messages.PLAYER_ADDED_TO_GAME));

            if (nextPlayerCount < tooManyPlayers) {
                System.out.println(Messages
                        .getMessage(Messages.ADD_ANOTHER_PLAYER));
            } else {
                break;
            }

            String response = scanner.next().toLowerCase();
            boolean addAnotherPlayer = (response.equals("y")
                    || response.equals("j"));
            if (addAnotherPlayer) {
                System.out.println(Messages.getMessage(Messages.ENTER_PLAYER)
                        + nextPlayerCount + Messages
                        .getMessage(Messages.PLAYER_USERNAME));
                nextPlayerCount++;
            } else {
                break;
            }
        }

        System.out.println(Messages.getMessage(Messages.START_GAME));
        for (String userName: userNameList) {
            System.out.println(userName);
        }
        scanner.close();
        return userNameList;
    }

    public void initializeGameState(List<String> usernames) {
        Setup setup = new Setup(usernames.size());
        this.users = setup.createUsers(usernames);
        String path = "src/main/resources/cards.csv";
        this.drawDeck = setup.createDrawDeck(new File(path));
        this.discardDeck = setup.createDiscardDeck();

        this.gameState = new GameState(this.users, this, this.drawDeck);
        setup.dealHands(this.users, this.drawDeck);
    }

    public void initializeGameView() {
        this.gameFrame = new JFrame();
        buildGameView();
    }

    public void displayFutureCards(List<Card> future) {
        JPanel popupPanel = new JPanel();
        for (int i=0; i<future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = createCardImage(topCard.getName(), i + "");
            popupPanel.add(futureCard);
        }
        JButton exit = new JButton("<html><center>Draw Deck <br> Done </center></html>");
        exit.setBackground(Color.GRAY);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.returnFutureCards(future);
            }
        });
        popupPanel.add(exit, BorderLayout.CENTER);

        gameFrame.add(popupPanel, BorderLayout.CENTER);
    }

    private void buildGameView() {
        if (gameFrame == null) {
            return;
        }

        int frameWidth = 1000;
        int frameHeight = 500;
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
                JButton otherPlayer =
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
        JButton discardPile = createCardImage("Top Card",
                this.discardDeck.getDeckSize() + "");
        tableAreaDisplayPanel.add(discardPile, BorderLayout.SOUTH);
        tableAreaDisplayPanel.add(deckButton, BorderLayout.NORTH);
        return tableAreaDisplayPanel;
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        JPanel playerDeckDisplayPanel = new JPanel();
        playerDeckDisplayPanel.setLayout(new BorderLayout());
        JLabel playerNameLabel =
                new JLabel(Messages.getMessage(Messages.YOUR_TURN)
                        + gameState.getUserForCurrentTurn().getName());

        playerDeckDisplayPanel.add(playerNameLabel, BorderLayout.NORTH);

        JPanel handDisplayPanel = new JPanel();
        handDisplayPanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        for (Card card : gameState.getUserForCurrentTurn().getHand()) {
            JButton cardLayout = createCardImage(card.getName(), "");
            cardLayout.getPreferredSize();
            cardLayout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {

                    if(cardLayout.getBackground()==Color.magenta){
                        System.out.println(card.getName()+" is selected!");
                        cardLayout.setBackground(Color.red);
                    }else{
                        System.out.println(card.getName()+" is deselected!");
                        cardLayout.setBackground(Color.magenta);
                    }
                }
            });
            handDisplayPanel.add(cardLayout);
        }
        playerDeckDisplayPanel.add(handDisplayPanel, BorderLayout.CENTER);
        return playerDeckDisplayPanel;
    }

    private JButton createDeckImage(String desc) {
        JButton deckImage = new JButton("<html><center>Draw Deck<br>"
                + desc + "</center></html>");
        deckImage.setBackground(Color.GREEN);
        return deckImage;
    }

    private JButton createCardImage(String name, String desc) {
        int cardWidth = 75;
        int cardHeight = 80;
        JButton cardImage = new JButton();
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
    public void updateUI() {
        buildGameView();
    }

    /**
     * These methods should only be used for Integration Testing
     * @return
     */
    public GameState getGameState(){
        return this.gameState;
    }
    public DrawDeck getDrawDeck(){ return this.drawDeck;}
    public User getCurrentUser(){ return this.users.peek();}
    public void initializeGameState() {
        Setup setup = new Setup(users.size());
        String path = "src/main/resources/cards.csv";
        this.drawDeck = setup.createDrawDeck(new File(path));
        this.discardDeck = setup.createDiscardDeck();

        this.gameState = new GameState(this.users, this, this.drawDeck);
        setup.dealHands(this.users, this.drawDeck);
    }
}

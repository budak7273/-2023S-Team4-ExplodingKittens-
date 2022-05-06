package presentation;

import datasource.Messages;
import system.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GamePlayer {

    /**This is the frame the game is made on.*/
    private final JFrame gameFrame = new JFrame();

    /**Local storage of the game's current state. */
    private GameState gameState;

    public void setGameState(final GameState currentGameState) {
        this.gameState = currentGameState;
    }

    public void displayFutureCards(List<Card> future) {
        JPanel popupPanel = new JPanel();
        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = createCardImage(
                    topCard.getName(), i + "");
            popupPanel.add(futureCard);
        }
        JButton exit = new JButton(
                "<html><center>Draw Deck <br> Done </center></html>");
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

    public void buildGameView() {
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
        for (User user: this.gameState.getPlayerQueue()) {
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
        JButton deckButton = createDeckImage(
                this.gameState.getDeckSizeForCurrentTurn() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gameState.drawCardForCurrentTurn();
            }
        });
        JButton discardPile = createCardImage("Top Card",
                 "");
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

                    if (cardLayout.getBackground() == Color.magenta) {
                        System.out.println(card.getName() + " is selected!");
                        cardLayout.setBackground(Color.red);
                    } else {
                        System.out.println(card.getName() + " is deselected!");
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
        final int cardWidth = 75;
        final int cardHeight = 80;
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
    public GameState getGameState() {
        return this.gameState;
    }
}

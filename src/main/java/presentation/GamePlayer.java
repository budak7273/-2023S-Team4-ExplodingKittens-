package presentation;

import system.Card;
import system.GameState;
import system.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePlayer {

    JFrame gameFrame;

    GameState gameState;

    public GamePlayer(JFrame gameFrame){
        this.gameFrame = gameFrame;
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
        JButton deckButton = createDeckImage(this.gameState.getDeckSizeForCurrentTurn() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gameState.drawCardForCurrentTurn();
                gameState.transitionToNextTurn();
            }
        });
        JPanel discardPile = createCardImage("Top Card",
                this.gameState.getTopDiscardedCardForCurrentTurn() + "");
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
    public void updateUI(GameState gameState) {
        this.gameState = gameState;
        buildGameView();
    }
}

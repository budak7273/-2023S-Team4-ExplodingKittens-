package presentation;

import datasource.Messages;
import system.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GamePlayer {

    /**
     * This is the frame the game is made on.
     */
    private final JFrame gameFrame = new JFrame();

    /**Panel that displays cards to be viewed, selected and edited.*/
    private JPanel selectionPanel = new JPanel();

    /**
     * Local storage of the game's current state.
     */
    private GameState gameState;

    private JPanel playerDeckDisplayPanel;

    private boolean catMode;

    private HashMap<Card, JButton> displayCards;
    private ArrayList<Card> selectedCards;

    public GamePlayer() {
        selectedCards = new ArrayList<>();
        displayCards = new HashMap<>();
    }

    public void setGameState(final GameState currentGameState) {
        this.gameState = currentGameState;
    }

    public void displayFutureCards(List<Card> future) {
        final int button_width = 150;
        final int button_height = 50;

        JPanel futurePanel = new JPanel();
        futurePanel.setLayout(new GridLayout(2, 1));
        JPanel futureCardPanel = new JPanel();
        futureCardPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JPanel futureClose = new JPanel();
        futureClose.setLayout(new FlowLayout());

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = createCardImage(
                    topCard.getName(), i + "");
            futureCardPanel.add(futureCard);
        }
        JButton exit = new JButton(
                "<html><center>Done</center></html>");
        exit.setPreferredSize(new Dimension(button_width, button_height));
        exit.setBackground(Color.GRAY);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (futurePanel.getParent().equals(selectionPanel)) {
                    selectionPanel.remove(futurePanel);
                    gameFrame.repaint();
                }

                gameState.returnFutureCards(future);
            }
        });
        futureClose.add(exit);

        futurePanel.add(futureCardPanel);
        futurePanel.add(futureClose);

        selectionPanel.add(futurePanel);
        gameFrame.repaint();
    }

    public void buildGameView() {
        final int frameWidth = 1000;
        final int frameHeight = 500;
        gameFrame.getContentPane().removeAll();
        JPanel userDisplayPanel = generateUserDisplayPanel();
        JPanel tableAreaDisplayPanel = generateTableAreaDisplayPanel();
        playerDeckDisplayPanel = generatePlayerDeckDisplayPanel();


        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(userDisplayPanel, BorderLayout.NORTH);
        gameFrame.add(tableAreaDisplayPanel, BorderLayout.CENTER);
        gameFrame.add(playerDeckDisplayPanel, BorderLayout.SOUTH);


        gameFrame.pack();
        gameFrame.setSize(frameWidth, frameHeight);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel generatePlayerSelectionPanel() {
        JPanel playerPanel = new JPanel();
        this.generateUserSelectionPanel(playerPanel, BorderLayout.WEST);

        return playerPanel;
    }

    private void generateUserSelectionPanel(JPanel p, String layout) {
        JPanel userSelectionPanel = new JPanel();
        JButton modeButton = createButtonImage(
                "Switch To Cat Mode");
        JButton confirmButton = createButtonImage(
                "Confirm");
        JButton endButton = createButtonImage(
                "End My Turn");
        this.setModeButtonListener(modeButton);
        this.setConfirmButtonListener(confirmButton, endButton);
        this.setEndButtonListener(endButton);

        userSelectionPanel.add(modeButton, BorderLayout.WEST);
        userSelectionPanel.add(confirmButton, BorderLayout.CENTER);
        userSelectionPanel.add(endButton, BorderLayout.EAST);
        p.add(userSelectionPanel, layout);
    }

    private void setEndButtonListener(JButton endButton) {
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (playerDeckDisplayPanel != null) {
                    playerDeckDisplayPanel.setVisible(false);
                }
            }
        });
    }

    private void setConfirmButtonListener(JButton confirmButton,
                                          JButton endButton) {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (catMode) {
                    System.out.println("CatMode.");
                } else {
                    handleSelectedCardsInNormalMode();
                }

            }

            private void handleSelectedCardsInNormalMode() {
                if (selectedCards.size() != 1) {
                    String infoMessage = Messages.getMessage(
                            Messages.WRONG_SELECTION_NORMAL_MODE);
                    String titleBar = "Warning";
                    JOptionPane.showMessageDialog(null, infoMessage,
                            "InfoBox: " + titleBar,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Card card = selectedCards.get(0);

                    if (card.isCatCard()) {
                        String infoMessage = Messages.getMessage(
                                Messages.CAT_SELECTION_NORMAL_MODE);
                        String titleBar = "Warning";
                        JOptionPane.showMessageDialog(null,
                                infoMessage,
                                "InfoBox: " + titleBar,
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (card.getType().getEffectPattern() != null) {
                            card.getType().getEffectPattern()
                                    .useEffect(gameState);
                            gameState.getUserForCurrentTurn()
                                    .removeCard(card);
                            System.out.println(selectedCards
                                    .get(0).getName()
                                    + " is removed from hand after use.");
                            generatePlayerDeckCardsPanel(
                                    playerDeckDisplayPanel,
                                    BorderLayout.CENTER);
                            displayCards.get(card).setVisible(false);
                            selectedCards.clear();
                            gameFrame.validate();
                            gameFrame.repaint();
                        }
                    }
                }
            }

        });
    }

    private void setModeButtonListener(JButton modeButton) {
        modeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (catMode) {
                    modeButton.setText(
                            Messages.getMessage(
                                    Messages.SWITCH_TO_CAT_MODE));
                } else {
                    modeButton.setText(
                            Messages.getMessage(
                                    Messages.SWITCH_TO_NORMAL_MODE));
                }
                catMode = !catMode;
            }
        });
    }

    private JPanel generateUserDisplayPanel() {
        JPanel userDisplayPanel = new JPanel();
        for (User user : this.gameState.getPlayerQueue()) {
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
        selectionPanel = new JPanel();
        selectionPanel.setLayout(new BorderLayout());
        JButton deckButton = createDeckImage(
                this.gameState.getDeckSizeForCurrentTurn() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                selectedCards.clear();
                gameState.drawCardForCurrentTurn();
            }
        });
        JButton discardPile = createCardImage("Top Card",
                "");
        selectionPanel.add(discardPile, BorderLayout.WEST);
        selectionPanel.add(deckButton, BorderLayout.EAST);

        JPanel playerSelectionPanel = generatePlayerSelectionPanel();
        selectionPanel.add(playerSelectionPanel, BorderLayout.SOUTH);

        return selectionPanel;
    }

    private void generatePlayerDeckCardsPanel(JPanel p, String layout) {
        JPanel playerDeckCardsPanel = new JPanel();
        playerDeckCardsPanel.setLayout(new BorderLayout());
        JLabel playerNameLabel =
                new JLabel(Messages.getMessage(Messages.YOUR_TURN)
                        + gameState.getUserForCurrentTurn().getName());

        playerDeckCardsPanel.add(playerNameLabel, BorderLayout.NORTH);

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
                        selectedCards.add(card);
                        cardLayout.setBackground(Color.red);
                    } else {
                        System.out.println(card.getName() + " is deselected!");
                        selectedCards.remove(card);
                        cardLayout.setBackground(Color.magenta);
                    }
                }
            });
            handDisplayPanel.add(cardLayout);
            displayCards.put(card, cardLayout);
        }
        playerDeckCardsPanel.add(handDisplayPanel, BorderLayout.CENTER);
        p.add(playerDeckCardsPanel, layout);
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        playerDeckDisplayPanel = new JPanel();

        this.generatePlayerDeckCardsPanel(
                playerDeckDisplayPanel,
                BorderLayout.CENTER);

        return playerDeckDisplayPanel;
    }

    private JButton createButtonImage(String btnName) {
        JButton btnImage = new JButton("<html><center>" + btnName + "<br>"
                + "</center></html>");
        btnImage.setBackground(Color.GRAY);
        return btnImage;
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
     *
     * @return
     */
    public GameState getGameState() {
        return this.gameState;
    }

    public void endGame() {
    }
}

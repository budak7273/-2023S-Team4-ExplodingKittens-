package presentation;

import datasource.Messages;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import system.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class GamePlayer {

    /**
     * This is the frame the game is made on.
     */
    private JFrame gameFrame;

    private NotificationPanel notificationPanel;

    /**
     * Local storage of the game's current state.
     */
    private GameState gameState;
    private JPanel playerDeckDisplayPanel;
    private boolean catMode;
    private HashMap<Card, JButton> displayCards;
    private ArrayList<Card> selectedCards;
    private static final int NOPE_DELAY_MILLIS = 2000;
    private Timer nopeTimer;
    private Card executingCard;

    public GamePlayer(JFrame frame) {
        this.gameFrame = frame;
        this.notificationPanel = new NotificationPanel(this);
        setSelectedCards(new ArrayList<>());
        displayCards = new HashMap<>();
        ActionListener nopeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tryTriggerCardExecution();
            }
        };
        nopeTimer = new Timer(NOPE_DELAY_MILLIS, nopeListener);
        nopeTimer.setRepeats(false);
    }

    public void setGameState(final GameState currentGameState) {
        this.gameState = currentGameState;
    }

    public void updateDisplay() {
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    public void buildGameView() {
        final int frameWidth = 1300;
        final int frameHeight = 800;
        gameFrame.getContentPane().removeAll();

        JPanel userDisplayPanel = generateUserDisplayPanel();
        JPanel tableAreaDisplayPanel = generateTableAreaDisplayPanel();
        playerDeckDisplayPanel = generatePlayerDeckDisplayPanel();

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(userDisplayPanel, BorderLayout.NORTH);
        gameFrame.add(tableAreaDisplayPanel, BorderLayout.CENTER);
        gameFrame.add(playerDeckDisplayPanel, BorderLayout.SOUTH);

        gameFrame.setSize(frameWidth, frameHeight);
        gameFrame.pack();

        playerDeckDisplayPanel.setVisible(false);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        JPanel tableAreaDisplayPanel = new JPanel();
        tableAreaDisplayPanel.setLayout(new BorderLayout());
        JButton deckButton = createDeckImage(
                this.gameState.getDeckSizeForCurrentTurn() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                getSelectedCards().clear();
                getNotificationPanel().removeAll();
                gameState.drawCardForCurrentTurn();
            }
        });
        JButton discardPile = createCardImage("Top Card",
                "");
        tableAreaDisplayPanel.add(discardPile, BorderLayout.WEST);
        tableAreaDisplayPanel.add(deckButton, BorderLayout.EAST);

        JPanel playerSelectionPanel = generateUserSelectionPanel();
        tableAreaDisplayPanel.add(playerSelectionPanel, BorderLayout.SOUTH);
        tableAreaDisplayPanel.add(getNotificationPanel());

        return tableAreaDisplayPanel;
    }

    private JPanel generatePlayerDeckDisplayPanel() {
        return generatePlayerDeckCardsPanel(
                BorderLayout.CENTER);
    }

    private JButton createDeckImage(String desc) {
        JButton deckImage = new JButton("<html><center>Draw Deck<br>"
                + desc + "</center></html>");
        deckImage.setBackground(Color.GREEN);
        return deckImage;
    }

    private JPanel generateUserSelectionPanel() {
        JPanel p = new JPanel(new GridLayout(2, 1));
        JPanel userSelectionPanel = new JPanel();

        JButton modeButton = createButtonImage(
                Messages.getMessage(
                        Messages.SWITCH_TO_CAT_MODE));
        JButton confirmButton = createButtonImage(
                "Confirm");
        JButton hideButton = createButtonImage(
                Messages.getMessage(
                        Messages.SWITCH_TO_SHOW_MODE));
        this.setModeButtonListener(modeButton);
        this.setConfirmButtonListener(confirmButton, hideButton);
        this.setEndButtonListener(hideButton);

        userSelectionPanel.add(modeButton, BorderLayout.WEST);
        userSelectionPanel.add(confirmButton, BorderLayout.CENTER);
        userSelectionPanel.add(hideButton, BorderLayout.EAST);
        p.add(userSelectionPanel, BorderLayout.WEST);

        JLabel playerNameLabel =
                new JLabel(Messages.getMessage(Messages.YOUR_TURN)
                        + gameState.getUserForCurrentTurn().getName());

        p.add(playerNameLabel, BorderLayout.SOUTH);
        return p;
    }

    private JButton createButtonImage(String btnName) {
        JButton btnImage = new JButton("<html><center>" + btnName + "<br>"
                + "</center></html>");
        btnImage.setBackground(Color.GRAY);
        return btnImage;
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

    private void setConfirmButtonListener(JButton confirmButton,
                                          JButton endButton) {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (catMode) {
                    String msg = "TODO: Implement handleSelectedCardsInCatMode";
                    System.out.println(msg);
                } else {
                    handleSelectedCardsInNormalMode();
                }
            }

            private void handleSelectedCardsInNormalMode() {
                if (getSelectedCards().size() != 1) {
                    String infoMessage = Messages.getMessage(
                            Messages.WRONG_SELECTION_NORMAL_MODE);
                    String titleBar = "InfoBox: Warning";
                    JOptionPane.showMessageDialog(null,
                            infoMessage, titleBar,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Card card = getSelectedCards().get(0);
                if (card.isCatCard()) {
                    String infoMessage = Messages.getMessage(
                            Messages.CAT_SELECTION_NORMAL_MODE);
                    String titleBar = "InfoBox: Warning";
                    JOptionPane.showMessageDialog(null,
                            infoMessage, titleBar,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (card.getType().getEffectPattern() == null) {
                    return;
                }

                executingCard = card;
                notificationPanel.lock();
                notificationPanel.notifyPlayers("Waiting for NOPEs...", "");
                gameState.setCardExecutionState(1);
                nopeTimer.start();

                updateUI();

                getSelectedCards().clear();
                gameFrame.validate();
                gameFrame.repaint();
            }
        });
    }

    private void setEndButtonListener(JButton hideButton) {
        hideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (playerDeckDisplayPanel != null) {
                    if (playerDeckDisplayPanel.isVisible()) {
                        playerDeckDisplayPanel.setVisible(false);
                        hideButton.setText(
                                Messages.getMessage(
                                        Messages.SWITCH_TO_SHOW_MODE));
                        updateDisplay();
                    } else {

                        playerDeckDisplayPanel.setVisible(true);
                        hideButton.setText(
                                Messages.getMessage(
                                        Messages.SWITCH_TO_HIDE_MODE));
                        updateDisplay();
                    }
                }
            }
        });
    }

    private JPanel generatePlayerDeckCardsPanel(String layout) {
        JPanel playerDeckCardsPanel = new JPanel();
        playerDeckCardsPanel.setLayout(new BorderLayout());

        JPanel handDisplayPanel = new JPanel();
        handDisplayPanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        for (Card card : gameState.getUserForCurrentTurn().getHand()) {
            JButton cardLayout = createCardImage(card.getName(),
                    card.getDesc());
            cardLayout.getPreferredSize();
            cardLayout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {

                    if (cardLayout.getBackground() == Color.magenta) {
                        System.out.println(card.getName() + " is selected!");
                        getSelectedCards().add(card);
                        cardLayout.setBackground(Color.red);
                    } else {
                        System.out.println(card.getName() + " is deselected!");
                        getSelectedCards().remove(card);
                        cardLayout.setBackground(Color.magenta);
                    }
                }
            });
            handDisplayPanel.add(cardLayout);
            displayCards.put(card, cardLayout);
        }
        playerDeckCardsPanel.add(handDisplayPanel, BorderLayout.CENTER);

        JPanel p = new JPanel();
        p.add(playerDeckCardsPanel, layout);
        return p;
    }

    protected JButton createCardImage(String name, String desc) {
        final int cardWidth = 150;
        final int cardHeight = 160;
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

    public void displayFutureCards(List<Card> future) {
        getNotificationPanel().seeTheFuture(future);
    }

    public void editFutureCards(List<Card> future) {
        getNotificationPanel().alterTheFuture(future);
    }

    public void returnFutureCards(List<Card> future) {
        gameState.returnFutureCards(future);
    }

    public void explosionNotification(boolean victimState) {
        String deathMessage;

        if (victimState) {
            deathMessage = Messages.getMessage(Messages.PLAYER_LOST_DEFUSE);
            AudioPlayer.playDefused();
        } else {
            deathMessage = Messages.getMessage(Messages.PLAYER_DIED);
            AudioPlayer.playExplosion();
        }

        getNotificationPanel().notifyPlayers(deathMessage, "rip");
    }

    public void tryTriggerCardExecution() {
        notificationPanel.removeAll();
        notificationPanel.unlock();
        if (gameState.getCardExecutionState() == 1) {
            executingCard.activateEffect(gameState);
        }
        executingCard = null;
        gameState.setCardExecutionState(-1);
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

    /**
     * Panel that displays cards to be viewed, selected and edited.
     */
    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }

    public void setNotificationPanel(NotificationPanel panel) {
        this.notificationPanel = panel;
    }

    public ArrayList<Card> getSelectedCards() {
        return selectedCards;
    }

    public void setSelectedCards(ArrayList<Card> cards) {
        this.selectedCards = cards;
    }

    public void displayTargetedAttackPrompt(List<User> users) {
        this.notificationPanel.displayTargetedAttackPrompt(users);
    }

    public void triggerTargetedAttackOn(User user) {
        System.out.println("TODO: triggerTargetedAttackOn user");
        gameState.executeTargetedAttackOn(user);
    }


}

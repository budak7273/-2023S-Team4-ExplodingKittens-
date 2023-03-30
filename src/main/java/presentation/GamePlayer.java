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
import java.util.Random;

import static javax.swing.ScrollPaneConstants.*;

public class GamePlayer {

    /**
     * This is the frame the game is made on.
     */
    private final JFrame gameFrame;

    private NotificationPanel notificationPanel;

    /**
     * Local storage of the game's current state.
     */
    private JComponent playerDeckDisplayPanel;
    private boolean catMode;
    private boolean enabled;
    private final HashMap<Card, JButton> displayCards;
    private ArrayList<Card> selectedCards;
    private Card executingCard;
    private final Object mutex = new Object();
    private GameManager gameManager;

    public GamePlayer(JFrame frame) {
        this.gameFrame = frame;
        this.enabled = true;
        this.notificationPanel = new NotificationPanel(this);
        setSelectedCards(new ArrayList<>());
        displayCards = new HashMap<>();

        final int frameWidth = 1300;
        final int frameHeight = 800;
        gameFrame.setSize(frameWidth, frameHeight);
    }

    public void setGameManager(final GameManager manager) {
        this.gameManager = manager;
    }

    public void updateDisplay() {
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    private void buildGameView() {
        gameFrame.getContentPane().removeAll();

        JPanel userDisplayPanel = generateUserDisplayPanel();
        JPanel tableAreaDisplayPanel = generateTableAreaDisplayPanel();
        playerDeckDisplayPanel = generatePlayerDeckDisplayPanel();

        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(userDisplayPanel, BorderLayout.NORTH);
        gameFrame.add(tableAreaDisplayPanel, BorderLayout.CENTER);
        gameFrame.add(playerDeckDisplayPanel, BorderLayout.SOUTH);

        playerDeckDisplayPanel.setVisible(false);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel generateUserDisplayPanel() {
        JPanel userDisplayPanel = new JPanel();
        for (User user : this.gameManager.getPlayerQueue()) {
            if (user != this.gameManager.getUserForCurrentTurn()) {
                JButton otherPlayer =
                        createCardImage(user.getName(),
                                user.getHand().size() + "");
                otherPlayer.addActionListener(new ActionListener() {
                    private final User innerUser = user;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        tryNope(innerUser);
                    }
                });
                userDisplayPanel.add(otherPlayer);
            }
        }

        return userDisplayPanel;
    }

    private JPanel generateTableAreaDisplayPanel() {
        JPanel tableAreaDisplayPanel = new JPanel();
        tableAreaDisplayPanel.setLayout(new BorderLayout());
        JButton deckButton = createDeckImage(
                this.gameManager.getDeckSizeForCurrentTurn() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (gameManager.getCardExecutionState() == -1) {
                    getSelectedCards().clear();
                    notificationPanel.removeAll();
                    gameManager.drawCardForCurrentTurn();
                }
            }
        });
        JButton discardPile = createCardImage(
                Messages.getMessage(Messages.TOP_CARD), "");
        this.setEnabledButton(discardPile);
        tableAreaDisplayPanel.add(discardPile, BorderLayout.WEST);
        tableAreaDisplayPanel.add(deckButton, BorderLayout.EAST);

        JPanel playerSelectionPanel = generateUserSelectionPanel();
        tableAreaDisplayPanel.add(playerSelectionPanel, BorderLayout.SOUTH);
        tableAreaDisplayPanel.add(notificationPanel);

        return tableAreaDisplayPanel;
    }

    private JComponent generatePlayerDeckDisplayPanel() {
        return generatePlayerDeckCardsPanel(
                BorderLayout.CENTER);
    }

    public void disableButtons() {
        this.enabled = false;
        this.updateUI();
        this.updateDisplay();
    }

    public void enableButtons() {
        this.enabled = true;
        this.updateUI();
        this.updateDisplay();
    }

    private JButton createDeckImage(String desc) {
        JButton deckImage = new JButton("<html><center>"
                + Messages.getMessage(Messages.DRAW_DECK)
                + "<br>"
                + desc + "</center></html>");
        deckImage.setBackground(Color.GREEN);
        this.setEnabledButton(deckImage);
        return deckImage;
    }

    private JPanel generateUserSelectionPanel() {
        final int fontSize = 30;
        final int width = 200;
        final int height = 500;
        JPanel p = new JPanel(new GridLayout(2, 1));
        JPanel labelPanel = new JPanel();
        JPanel userSelectionPanel = new JPanel();

        JButton modeButton = createButtonImage(
                Messages.getMessage(
                        Messages.SWITCH_TO_CAT_MODE));
        if (catMode) {
            modeButton.setText(Messages.getMessage(
                    Messages.SWITCH_TO_NORMAL_MODE));
        }
        JButton confirmButton = createButtonImage(
                Messages.getMessage(Messages.CONFIRM));
        JButton hideButton = createButtonImage(
                Messages.getMessage(
                        Messages.SWITCH_TO_SHOW_MODE));

        this.setEnabledButton(modeButton);
        this.checkCatModeAccessibility(modeButton);
        this.setEnabledButton(confirmButton);
        this.setEnabledButton(hideButton);

        this.setModeButtonListener(modeButton);
        this.setConfirmButtonListener(confirmButton, hideButton);
        this.setEndButtonListener(hideButton);

        JLabel playerNameLabel =
                new JLabel(Messages.getMessage(Messages.YOUR_TURN)
                        + " " + gameManager.getUserForCurrentTurn().getName());
        playerNameLabel.setFont(new Font("Sans Serif", Font.BOLD, fontSize));
        labelPanel.add(playerNameLabel, BorderLayout.WEST);
        p.add(labelPanel);
        userSelectionPanel.add(modeButton, BorderLayout.WEST);
        userSelectionPanel.add(confirmButton, BorderLayout.CENTER);
        userSelectionPanel.add(hideButton, BorderLayout.EAST);
        p.add(userSelectionPanel, BorderLayout.WEST);

        p.setSize(width, height);
        return p;
    }

    private void checkCatModeAccessibility(JButton modeButton) {
        if (!catMode && !this.gameManager.getUserForCurrentTurn()
                .checkForSpecialEffectPotential()) {
            modeButton.setEnabled(false);
            modeButton.setBackground(Color.GRAY);
        }
    }

    private void setEnabledButton(JButton button) {
        button.setEnabled(enabled);
        if (!enabled) {

            button.setBackground(Color.GRAY);
        }
    }

    private JButton createButtonImage(String btnName) {
        JButton btnImage = new JButton("<html><center>" + btnName + "<br>"
                + "</center></html>");
        btnImage.setBackground(Color.GREEN);
        this.setEnabledButton(btnImage);
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
                    if (gameManager.getCardExecutionState() == -1) {
                        if (catMode) {
                            handleSelectedCardsInCatMode();
                        } else {
                            handleSelectedCardsInNormalMode();
                        }
                    }
            }

            private void handleSelectedCardsInCatMode() {
                if (getSelectedCards().size() != 2) {
                    displayWrongSelectionPromptInCatMode();
                    return;
                }
                Card c1 = getSelectedCards().get(0);
                Card c2 = getSelectedCards().get(1);
                User current = gameManager.getUserForCurrentTurn();
                if (current.checkCatPairMatch(c1, c2)) {

                    gameManager.triggerDisplayOfCatStealPrompt();
                    current.removeCard(c1);
                    current.removeCard(c2);

                    updateUI();

                    getSelectedCards().clear();
                    gameFrame.validate();
                    gameFrame.repaint();
                } else {
                    displayWrongSelectionPromptInCatMode();
                }

            }

            private void displayWrongSelectionPromptInCatMode() {
                String infoMessage = Messages.getMessage(
                        Messages.WRONG_SELECTION_CAT_MODE);
                String titleBar = "InfoBox: Warning";
                JOptionPane.showMessageDialog(null,
                        infoMessage, titleBar,
                        JOptionPane.INFORMATION_MESSAGE);
            }

            private void handleSelectedCardsInNormalMode() {
                if (getSelectedCards().size() != 1) {
                    String infoMessage = Messages.getMessage(
                            Messages.WRONG_SELECTION_NORMAL_MODE);
                    String titleBar = Messages.getMessage(Messages.WARNING);
                    JOptionPane.showMessageDialog(null,
                            infoMessage, titleBar,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Card card = getSelectedCards().get(0);
                if (card.isCatCard()) {
                    String infoMessage = Messages.getMessage(
                            Messages.CAT_SELECTION_NORMAL_MODE);
                    String titleBar = Messages.getMessage(Messages.WARNING);
                    JOptionPane.showMessageDialog(null,
                            infoMessage, titleBar,
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (card.getType().getEffectPattern() == null) {
                    return;
                }

                executingCard = card;
                nopeMessage(false);
                synchronized (mutex) {
                    gameManager.setCardExecutionState(1);
                }

                updateUI();

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

    private JComponent generatePlayerDeckCardsPanel(String layout) {
        JPanel playerDeckCardsPanel = new JPanel();
        playerDeckCardsPanel.setLayout(new BorderLayout());

        JPanel handDisplayPanel = new JPanel();
        handDisplayPanel.setComponentOrientation(
                ComponentOrientation.LEFT_TO_RIGHT);
        for (Card card : gameManager.getUserForCurrentTurn().getHand()) {
            JButton cardLayout = createCardImage(card.getName(),
                    card.getDesc());
            cardLayout.getPreferredSize();
            cardLayout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {

                    if (cardLayout.getBackground() == Color.CYAN) {
                        System.out.println(card.getName() + " is selected!");
                        getSelectedCards().add(card);
                        cardLayout.setBackground(Color.MAGENTA);
                    } else {
                        System.out.println(card.getName() + " is deselected!");
                        getSelectedCards().remove(card);
                        cardLayout.setBackground(Color.CYAN);
                    }
                }
            });
            handDisplayPanel.add(cardLayout);
            displayCards.put(card, cardLayout);
        }
        playerDeckCardsPanel.add(handDisplayPanel, BorderLayout.CENTER);

        JPanel p = new JPanel();
        p.add(playerDeckCardsPanel, layout);

        JScrollPane scroll = new JScrollPane(p);
        scroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    protected JButton createCardImage(String name, String desc) {
        final int cardWidth = 150;
        final int cardHeight = 160;
        JButton cardImage = new JButton();
        cardImage.setLayout(new GridLayout(0, 1));
        cardImage.setPreferredSize(new Dimension(cardWidth, cardHeight));
        cardImage.setBackground(Color.CYAN);
        JLabel cardDetails = new JLabel();
        cardDetails.setText("<html><overflow='hidden'>"
                + name + "<br>" + desc + "</html>");
        cardImage.add(cardDetails);
        return cardImage;
    }

    public void displayFutureCards(List<Card> future) {
        notificationPanel.seeTheFuture(future);
    }

    public void editFutureCards(List<Card> future) {
        notificationPanel.alterTheFuture(future);
    }

    public void returnFutureCards(List<Card> future) {
        gameManager.returnFutureCards(future);
    }

    public void explosionNotification(boolean victimState) {
        String deathMessage;

        if (victimState) {
            deathMessage = Messages.getMessage(Messages.PLAYER_LOST_DEFUSE);
            DrawDeck deck = gameManager.getDrawDeck();
            notificationPanel
                    .addExplodingKittenBackIntoDeck(deathMessage, deck);
            AudioPlayer.playDefused();
        } else {
            deathMessage = Messages.getMessage(Messages.PLAYER_DIED);
            AudioPlayer.playExplosion();
            gameManager.transitionToNextTurn();
            notificationPanel.notifyPlayers(deathMessage,
                    Messages.getMessage(Messages.RIP));
        }


    }

    public void addExplodingKittenIntoDeck(Integer location) {
        gameManager.addExplodingKittenBackIntoDeck(location);
    }


    private void tryTriggerCardExecution() {
        notificationPanel.removeAll();
        synchronized (mutex) {
            if (gameManager.getCardExecutionState() == 1) {
                executingCard.activateEffect(gameManager);
            } else {
                gameManager.removeCardFromCurrentUser(executingCard);
            }
            executingCard = null;
            gameManager.setCardExecutionState(-1);
        }
        updateUI();
        getSelectedCards().clear();
    }

    private void tryNope(User executingUser) {
        synchronized (mutex) {
            int execution = gameManager.getCardExecutionState();
            if (execution == 0) {
                if (executingUser.attemptToNope()) {
                    nopeMessage(false);
                    gameManager.setCardExecutionState(1);
                }
            } else if (execution == 1) {
                if (executingUser.attemptToNope()) {
                    nopeMessage(true);
                    gameManager.setCardExecutionState(0);
                }
            }
        }
    }

    private void nopeMessage(boolean currentNope) {
        String status;
        if (currentNope) {
            status = Messages.getMessage(Messages.NOPE_STATUS_MESSAGE);
        } else {
            status = Messages.getMessage(Messages.NOPE_STATUS_MESSAGE_NOT);
        }

        notificationPanel.notifyPlayers(status, "");
        notificationPanel.addExitButtonToLayout(Messages.getMessage(Messages.COUNTER_NOPE),
                e -> tryNope(gameManager.getUserForCurrentTurn()));
        notificationPanel.addExitButtonToLayout(Messages.getMessage(Messages.NO_MORE_NOPES),
                e -> this.noNopes());
        updateDisplay();
    }

    private void noNopes() {
        notificationPanel.removeAll();
        tryTriggerCardExecution();
    }

    /**
     * updateUI changes the GUI of the current game when it is called.
     */
    public void updateUI() {
        buildGameView();
    }

    public void displayWinForUser(User winner) {
        this.gameFrame.dispose();
        JOptionPane.showMessageDialog(null,
                                      winner.getName()
                                      + Messages.getMessage(Messages.WINNER_MESSAGE), null,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Panel that displays cards to be viewed, selected and edited.
     */
    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
    }

    private ArrayList<Card> getSelectedCards() {
        return selectedCards;
    }

    private void setSelectedCards(ArrayList<Card> cards) {
        this.selectedCards = cards;
    }

    public void displayTargetedAttackPrompt(List<User> users) {
        this.notificationPanel.displayTargetedAttackPrompt(users);
    }

    public void triggerTargetedAttackOn(User user) {
        gameManager.executeTargetedAttackOn(user);
    }

    public void triggerFavorOn(User user) {
        gameManager.executeFavorOn(user);
    }


    public void displayFavorPrompt(List<User> users) {
        this.notificationPanel.displayFavorPrompt(users);
    }

    public void displayCatStealPrompt(List<User> users) {
        this.notificationPanel.displayCatStealPrompt(users);
    }

    public int inputForStealCard(User user) {
        int result = 0;
        try {
            String inputs = (String) JOptionPane.showInputDialog(
                    gameFrame,
                    Messages.getMessage(Messages.VALID_INDEX),
                    Messages.getMessage(Messages.STEALING),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    1
            );
            result = Integer.parseInt(inputs) - 1;

            if (result < 0 || result >= user.getHand().size()) {
                String infoMessage = Messages.getMessage(
                        Messages.WRONG_INDEX_ENTERED);
                String titleBar = Messages.getMessage(Messages.WARNING);
                JOptionPane.showMessageDialog(null,
                        infoMessage, titleBar,
                        JOptionPane.INFORMATION_MESSAGE);
                result = -1;
            }

        } catch (HeadlessException e) {

        } catch (NumberFormatException e) {
            result = -1;
        }

        return result;
    }


    public void triggerCatStealOn(User user) {
        gameManager.executeCatStealOn(user, new Random());
    }

    public void toggleCatMode() {
        this.catMode = false;
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }
}

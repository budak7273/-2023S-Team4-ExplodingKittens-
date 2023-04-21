package presentation;

import datasource.CardType;
import datasource.I18n;
import system.*;
import system.messages.EventMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static javax.swing.ScrollPaneConstants.*;

public class GameWindow {

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
    private final Object nopeMutex = new Object();
    private GameManager gameManager;
    private final boolean muteAudio;
    private AudioPlayer audioPlayer;
    private JButton modeButton;

    public GameWindow(JFrame frame, boolean muteAudioInput) {
        this.gameFrame = frame;
        this.muteAudio = muteAudioInput;
        this.enabled = true;
        this.notificationPanel = new NotificationPanel(this);
        this.audioPlayer = new AudioPlayer(!muteAudio);
        setSelectedCards(new ArrayList<>());
        displayCards = new HashMap<>();

        final int frameWidth = 1300;
        final int frameHeight = 800;
        gameFrame.setSize(frameWidth, frameHeight);
        audioPlayer.playMusicOnStartup();
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
                JButton otherPlayer = createCardImage(user.getName(), user.getHandCount() + "");
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

    private void notifyUserOfCardDrawn() {
        gameManager.setCardExecutionState(ExecutionState.ACTIVATED_EFFECT);
    }

    public void clearCardDisplay() {
        gameManager.setCardExecutionState(ExecutionState.CLEAR);
        notificationPanel.removeAll();
        enableButtons();
        gameManager.transitionToNextTurn();
    }

    private JPanel generateTableAreaDisplayPanel() {
        JPanel tableAreaDisplayPanel = new JPanel();
        tableAreaDisplayPanel.setLayout(new BorderLayout());
        JButton deckButton = createDeckImage(
                this.gameManager.getDeckSizeForCurrentTurn() + "");
        deckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (gameManager.getCardExecutionState() == ExecutionState.CLEAR) {
                    getSelectedCards().clear();
                    notificationPanel.removeAll();
                    boolean drawnExploding = gameManager.drawCardForCurrentTurn();
                    if (!drawnExploding) {
                        Card drawnCard =
                                gameManager.getUserForCurrentTurn().getLastCardInHand();
                        notificationPanel.notifyPlayers(I18n.getMessage("CardDrawn") + drawnCard.getName(), "");
                        notificationPanel.addExitButtonToLayout(I18n.getMessage("Confirm"),
                                                                e2 -> clearCardDisplay());
                        notificationPanel.updateUI();
                        disableButtons();
                        notifyUserOfCardDrawn();
                    }
                }
            }
        });
        JButton discardPile = createCardImage(
                I18n.getMessage("TopCard"), "");
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
                                        + I18n.getMessage("DrawDeck") + "<br>"
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

        modeButton = createButtonImage(I18n.getMessage("SwitchToCatModeMessage"));
        if (catMode) {
            modeButton.setText(I18n.getMessage("SwitchToNormalModeMessage"));
        }
        JButton confirmButton = createButtonImage(I18n.getMessage("Confirm"));
        JButton hideButton = createButtonImage(I18n.getMessage("SwitchToShowModeMessage"));

        this.setEnabledButton(modeButton);
        this.checkCatModeAccessibility();
        this.setEnabledButton(confirmButton);
        this.setEnabledButton(hideButton);

        this.setModeButtonListener();
        this.setConfirmButtonListener(confirmButton, hideButton);
        this.setEndButtonListener(hideButton);

        JLabel playerNameLabel = new JLabel(I18n.getMessage("YourTurnMessage") + " "
                                            + gameManager.getUserForCurrentTurn().getName());
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

    private void checkCatModeAccessibility() {
        if (!catMode && !this.gameManager.checkCurrentUsersSpecialEffect()) {
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

    private void setModeButtonListener() {
        modeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (catMode) {
                    modeButton.setText(I18n.getMessage("SwitchToCatModeMessage"));
                } else {
                    modeButton.setText(I18n.getMessage("SwitchToNormalModeMessage"));
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
                if (gameManager.getCardExecutionState() == ExecutionState.CLEAR) {
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
                    gameManager.postMessage(EventMessage.publicMessage(
                            String.format(I18n.getMessage("CatComboDetailsPublic"),
                                          current.getName(), c1.getName(), c2.getName())));
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
                String infoMessage = I18n.getMessage("WrongSelectionCatModeMessage");
                String titleBar = "InfoBox: Warning";
                displayInformationalMessage(infoMessage, titleBar);
            }

            private void handleSelectedCardsInNormalMode() {
                if (getSelectedCards().size() != 1) {
                    String infoMessage = I18n.getMessage("WrongSelectionNormalModeMessage");
                    String titleBar = I18n.getMessage("Warning");
                    displayInformationalMessage(infoMessage, titleBar);
                    return;
                }

                Card card = getSelectedCards().get(0);

                if (card.isCatCard()) {
                    String infoMessage = I18n.getMessage("CatSelectionNormalModeMessage");
                    String titleBar = I18n.getMessage("Warning");
                    displayInformationalMessage(infoMessage, titleBar);
                    return;
                }

                if (card.getType() == CardType.DEFUSE) {
                    String infoMessage = I18n.getMessage("DefuseWithoutExplodingKittenMessage");
                    String titleBar = I18n.getMessage("Warning");
                    displayInformationalMessage(infoMessage, titleBar);
                    return;
                }

                if (card.getType() == CardType.NOPE) {
                    String infoMessage = I18n.getMessage("NopeDuringNormalTurnMessage");
                    String titleBar = I18n.getMessage("Warning");
                    displayInformationalMessage(infoMessage, titleBar);
                    return;
                }

                if (card.getType().getEffectPattern() == null) {
                    return;
                }

                executingCard = card;
                gameManager.postMessage(EventMessage.publicMessage(
                        String.format(I18n.getMessage("AnyoneNopePublic"),
                                      gameManager.getUserForCurrentTurn().getName(),
                                      executingCard.getName())
                ));
                nopeMessage(false, gameManager.getUserForCurrentTurn().getName());
                synchronized (nopeMutex) {
                    gameManager.setCardExecutionState(ExecutionState.ACTIVATED_EFFECT);
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
                        hideButton.setText(I18n.getMessage("SwitchToShowModeMessage"));
                        updateDisplay();
                    } else {
                        playerDeckDisplayPanel.setVisible(true);
                        hideButton.setText(I18n.getMessage("SwitchToHideModeMessage"));
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
            JButton cardLayout = createCardImage(card.getName(), card.getDesc());
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

                    if (getSelectedCards().size() >= 2 && gameManager.checkCurrentUsersSpecialEffect()) {
                        setCatMode(true);
                    } else {
                        setCatMode(false);
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
            deathMessage = I18n.getMessage("PlayerLostDefuse");
            gameManager.postMessage(EventMessage.publicMessage(
                    String.format(I18n.getMessage("DefuseUsedPublic"), gameManager.getUserForCurrentTurn().getName())));
            DrawDeck deck = gameManager.getDrawDeck();
            notificationPanel.addExplodingKittenBackIntoDeck(deathMessage, deck);
            audioPlayer.playDefused();
        } else {
            deathMessage = I18n.getMessage("PlayerDied");
            gameManager.postMessage(EventMessage.publicMessage(
                    String.format(I18n.getMessage("BlowUpPublic"), gameManager.getUserForCurrentTurn().getName())));
            audioPlayer.playExplosion();
            gameManager.transitionToNextTurn();
            notificationPanel.notifyPlayers(deathMessage, I18n.getMessage("Rip"));
        }
    }

    public void addExplodingKittenIntoDeck(Integer location) {
        gameManager.addExplodingKittenBackIntoDeck(location);
    }


    private void tryTriggerCardExecution() {
        notificationPanel.removeAll();
        synchronized (nopeMutex) {
            if (gameManager.getCardExecutionState() == ExecutionState.ACTIVATED_EFFECT) {
                executingCard.activateEffect(gameManager);
            } else {
                gameManager.postMessage(EventMessage.publicMessage(
                        String.format(I18n.getMessage("PlayBlockedByNopePublic"),
                                      gameManager.getUserForCurrentTurn().getName(),
                                      executingCard.getName())
                ));
                gameManager.removeCardFromCurrentUser(executingCard);
            }
            executingCard = null;
            gameManager.setCardExecutionState(ExecutionState.CLEAR);
        }
        updateUI();
        getSelectedCards().clear();
    }

    private void tryNope(User executingUser) {
        synchronized (nopeMutex) {
            ExecutionState execution = gameManager.getCardExecutionState();
            if (execution == ExecutionState.NORMAL) {
                if (executingUser.attemptToNope()) {
                    nopeMessage(false, executingUser.getName());
                    gameManager.setCardExecutionState(ExecutionState.ACTIVATED_EFFECT);
                }
            } else if (execution == ExecutionState.ACTIVATED_EFFECT) {
                if (executingUser.attemptToNope()) {
                    nopeMessage(true, executingUser.getName());
                    gameManager.setCardExecutionState(ExecutionState.NORMAL);
                }
            }
        }
    }

    public String buildNopeMessage(String executingUsername) {
        return I18n.getMessage("NopeStatusMessage") + "<br>"
               + executingUsername + " " + I18n.getMessage("WhoNoped");
    }

    public void nopeMessage(boolean currentNope, String executingUsername) {
        String status;
        if (currentNope) {
            status = buildNopeMessage(executingUsername);
            gameManager.postMessage(EventMessage.publicMessage(
                    String.format(I18n.getMessage("IsNopedPublic"), executingUsername)));
        } else {
            status = I18n.getMessage("NopeStatusMessageNot");
            gameManager.postMessage(EventMessage.publicMessage(I18n.getMessage("NotNopedPublic")));
        }
        notificationPanel.notifyPlayers(status, "");
        notificationPanel.addExitButtonToLayout(I18n.getMessage("counter.nope"),
                                                e -> tryNope(gameManager.getUserForCurrentTurn()));
        notificationPanel.addExitButtonToLayout(I18n.getMessage("no.more.nopes"),
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
        String infoMessage = winner.getName() + I18n.getMessage("WinnerMessage");
        displayInformationalMessage(infoMessage, "");
    }

    private void displayInformationalMessage(String message, String title) {
        if (!muteAudio) {
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Panel that displays cards to be viewed, selected and edited.
     */
    public NotificationPanel getNotificationPanel() {
        return notificationPanel;
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
                    I18n.getMessage("ValidIndex"),
                    I18n.getMessage("Stealing"),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    1
            );
            result = Integer.parseInt(inputs) - 1;

            if (result < 0 || result >= user.getHand().size()) {
                String infoMessage = I18n.getMessage("WrongIndexMessage");
                String titleBar = I18n.getMessage("Warning");
                displayInformationalMessage(infoMessage, titleBar);
                result = -1;
            }
        } catch (NumberFormatException e) {
            result = -1;
        }

        return result;
    }


    public void triggerCatStealOn(User user) {
        gameManager.executeCatStealOn(user, new Random());
    }

    private void setCatMode(boolean newCatMode) {
        catMode = newCatMode;

        if (modeButton != null) {
            if (!catMode) {
                modeButton.setText(I18n.getMessage("SwitchToCatModeMessage"));
            } else {
                modeButton.setText(I18n.getMessage("SwitchToNormalModeMessage"));
            }
        }
    }

    public void disableCatMode() {
        this.setCatMode(false);
    }

    public GameManager getGameManager() {
        return this.gameManager;
    }
}

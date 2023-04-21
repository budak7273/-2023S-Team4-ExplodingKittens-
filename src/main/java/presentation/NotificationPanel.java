package presentation;

import datasource.CardType;
import datasource.I18n;
import system.Card;
import system.DrawDeck;
import system.User;
import system.cardEffects.EffectPattern;
import system.cardEffects.UserTargetingEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NotificationPanel extends JPanel {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private final ArrayList<Card> cardOrder = new ArrayList<>();
    private final GameWindow gameWindow;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public NotificationPanel(GameWindow player) {
        super();
        this.gameWindow = player;
        constructBaseLayout();
    }

    private void constructBaseLayout() {
        this.removeAll();
        this.setLayout(new GridLayout(2, 1));

        contentPanel = new JPanel();
        ComponentOrientation orientation = ComponentOrientation.LEFT_TO_RIGHT;
        contentPanel.setComponentOrientation(orientation);
        this.add(contentPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        this.add(buttonPanel);
    }

    protected void addExitButtonToLayout(String msg, ActionListener eventFn) {
        JButton exit = new JButton();
        exit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        exit.setBackground(Color.GREEN);
        exit.addActionListener(eventFn);
        exit.setText(msg);
        buttonPanel.add(exit);
    }

    public void seeTheFuture(List<Card> future) {
        gameWindow.disableButtons();
        initializePane();
        addExitButtonToLayout(I18n.getMessage("Done"),
                e -> {
        removeAll();

            cardOrder.clear();
            gameWindow.enableButtons();
        });

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gameWindow.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            contentPanel.add(futureCard);
        }
    }

    public void alterTheFuture(List<Card> future) {
        gameWindow.disableButtons();
        initializePane();
        ActionListener eventFn = e -> {
            removeAll();

            if (cardOrder.size() > 0) {
                gameWindow.returnFutureCards(cardOrder);
                cardOrder.clear();
            }
            gameWindow.enableButtons();
        };
        addExitButtonToLayout(I18n.getMessage("Done"), eventFn);

        final Card[] selectedCard = {null};
        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gameWindow.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            futureCard.addActionListener(new ActionListener() {
                private final Card card = topCard;

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selectedCard[0] != null) {
                        int current = cardOrder.indexOf(card);
                        int swap = cardOrder.indexOf(selectedCard[0]);
                        cardOrder.set(swap, card);
                        cardOrder.set(current, selectedCard[0]);
                        selectedCard[0] = null;
                        alterTheFuture(new ArrayList<>(cardOrder));
                    } else {
                        selectedCard[0] = card;
                    }
                }
            });
            contentPanel.add(futureCard);
        }

        gameWindow.updateDisplay();
    }

    public void displayTargetedAttackPrompt(List<User> victims) {
        displaySingleSelectionPrompt(victims, CardType.TARGETED_ATTACK, null);
    }

    public void addExplodingKittenBackIntoDeck(String contentMessage,
                                               DrawDeck deck) {
        gameWindow.disableButtons();
        initializePane();
        boolean lastCard = false;
        int size = deck.getDeckSize();

        if (size == 0) {
            lastCard = true;
        }
        if (lastCard) {
            addExitButtonToLayout(I18n.getMessage("KittenPlaced"),
                    e -> {
                        removeAll();
                        gameWindow.addExplodingKittenIntoDeck(0);
                        gameWindow.enableButtons();
                    });
        } else {
            String[] options = new String[size];

            options[0] = "" + 0;
            for (int i = 0; i < size; i++) {
                options[i] = i + "";
            }

            JLabel content = new JLabel("<html><center><br>"
                    + contentMessage + "<br><br></center></html>");

            contentPanel.add(content);
            content.setOpaque(true);
            content.setBackground(Color.CYAN);

            gameWindow.updateDisplay();

            addExitButtonToLayout(I18n.getMessage("Location"),
                    e -> {
                        String getLocation = (String)
                                JOptionPane.showInputDialog(
                                        null,
                                        I18n.getMessage("PlaceKitten"),
                                        I18n.getMessage("KittenPlaced"),
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        options,
                                        options[0]);

                        removeAll();
                        gameWindow.addExplodingKittenIntoDeck(
                                Integer.parseInt(getLocation));
                        gameWindow.enableButtons();
                    });
        }
        gameWindow.updateDisplay();
    }

    public void notifyPlayers(String contentMessage, String doneMessage) {
        initializePane();
        if (!doneMessage.isEmpty()) {
            addExitButtonToLayout(doneMessage, e -> removeAll());
        }

        JLabel content = new JLabel("<html><center><br>"
                + contentMessage + "<br><br></center></html>");

        contentPanel.add(content);
        content.setOpaque(true);
        content.setBackground(Color.CYAN);

        gameWindow.updateDisplay();
    }

    private void initializePane() {
        constructBaseLayout();
        cardOrder.clear();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        gameWindow.updateDisplay();
    }

    public void displayFavorPrompt(List<User> victims) {
        // TODO switch to then() framework
        displaySingleSelectionPrompt(victims, CardType.FAVOR, null);

    }

    public void displayCatStealPrompt(List<User> victims) {
        // TODO switch to then() framework
        displaySingleSelectionPrompt(victims, CardType.FERAL_CAT, null);
    }

    public void displaySingleSelectionPrompt(List<User> victims, CardType type, Function<User, Void> then) {
        gameWindow.disableButtons();
        initializePane();

        final User[] selectedVictim = {null};
        final JButton[] selectedVictimBtn = {null};
        ActionListener eventFn = e -> {
            if (selectedVictim[0] == null) {
                return;
            }
            removeAll();

            if (type == CardType.FAVOR) {
                gameWindow.triggerFavorOn(selectedVictim[0]);
            } else if (type == CardType.FERAL_CAT) {
                gameWindow.triggerCatStealOn(selectedVictim[0]);
            } else {
                // TODO cleanup
                if (then != null) {
                    then.apply(selectedVictim[0]);
                } else {
                    gameWindow.triggerTargetedAttackOn(selectedVictim[0]);
                }
            }
            gameWindow.enableButtons();
        };
        addExitButtonToLayout(I18n.getMessage("Confirm"), eventFn);

        for (User victim : victims) {
            JButton victimBtn = gameWindow.createCardImage(
                    victim.getName(), "");

            if (type == CardType.FAVOR && victim.isEmptyHand()) {
                victimBtn.setEnabled(false);
            }

            victimBtn.addActionListener(e -> {
                if (selectedVictim[0] != null) {
                    selectedVictimBtn[0].setBackground(Color.CYAN);
                }
                selectedVictim[0] = victim;
                selectedVictimBtn[0] = victimBtn;
                victimBtn.setBackground(Color.red);
            });
            contentPanel.add(victimBtn);
        }

        gameWindow.updateDisplay();
    }


}

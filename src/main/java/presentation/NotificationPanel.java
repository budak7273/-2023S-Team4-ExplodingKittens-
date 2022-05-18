package presentation;

import datasource.CardType;
import system.Card;
import system.DrawDeck;
import system.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NotificationPanel extends JPanel {
    private static final int BUTTON_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 50;
    private final ArrayList<Card> cardOrder = new ArrayList<>();
    private final GamePlayer gamePlayer;
    private JPanel contentPanel;
    private JPanel buttonPanel;

    public NotificationPanel(GamePlayer player) {
        super();
        this.gamePlayer = player;
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
        gamePlayer.disableButtons();
        initializePane();
        addExitButtonToLayout("Done", e -> {
removeAll();

            if (cardOrder.size() > 0) {
                gamePlayer.returnFutureCards(cardOrder);
                cardOrder.clear();
            }
            gamePlayer.enableButtons();
        });

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            contentPanel.add(futureCard);
        }
    }

    public void alterTheFuture(List<Card> future) {
        gamePlayer.disableButtons();
        initializePane();
        ActionListener eventFn = e -> {
            removeAll();

            if (cardOrder.size() > 0) {
                gamePlayer.returnFutureCards(cardOrder);
                cardOrder.clear();
            }
            gamePlayer.enableButtons();
        };
        addExitButtonToLayout("Done", eventFn);

        final Card[] selectedCard = {null};
        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
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

        gamePlayer.updateDisplay();
    }

    public void displayTargetedAttackPrompt(List<User> victims) {
        displaySingleSelectionPrompt(victims, CardType.TARGETED_ATTACK);
    }

    public void addExplodingKittenBackIntoDeck(String contentMessage, DrawDeck deck){
        gamePlayer.disableButtons();
        initializePane();

        int size = deck.getDeckSize();
        String[] options = new String[size];
        options[0] = "" +0 ;
        for(int i = 0; i < size; i++){
            options[i] = i + "";
        }

        JLabel content = new JLabel("<html><center><br>"
                + contentMessage + "<br><br></center></html>");

        contentPanel.add(content);
        content.setOpaque(true);
        content.setBackground(Color.CYAN);

        gamePlayer.updateDisplay();
        addExitButtonToLayout("Select Location for Exploding Kitten in Deck", e -> {
            String getLocation = (String) JOptionPane.showInputDialog(null,
                    "Where do you want to place the kitten?",
                    "Place Exploding Kitten",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            removeAll();
            gamePlayer.addExplodingKittenIntoDeck(Integer.parseInt(getLocation));
            gamePlayer.enableButtons();});
        gamePlayer.updateDisplay();
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

        gamePlayer.updateDisplay();
    }

    private void initializePane() {
        constructBaseLayout();
        cardOrder.clear();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        gamePlayer.updateDisplay();
    }

    public void displayFavorPrompt(List<User> victims) {
        displaySingleSelectionPrompt(victims, CardType.FAVOR);

    }

    private void displaySingleSelectionPrompt(
            List<User> victims, CardType type) {
        gamePlayer.disableButtons();
        initializePane();

        final User[] selectedVictim = {null};
        final JButton[] selectedVictimBtn = {null};
        ActionListener eventFn = e -> {
            if (selectedVictim[0] == null) {
                return;
            }
            removeAll();
            if (type == CardType.FAVOR) {
                gamePlayer.triggerFavorOn(selectedVictim[0]);
            } else {
                gamePlayer.triggerTargetedAttackOn(selectedVictim[0]);
            }
            gamePlayer.enableButtons();
        };
        addExitButtonToLayout("Confirm", eventFn);

        for (User victim : victims) {
            JButton victimBtn = gamePlayer.createCardImage(
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

        gamePlayer.updateDisplay();
    }
}

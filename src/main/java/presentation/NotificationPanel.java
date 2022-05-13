package presentation;

import system.Card;

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
    private final JPanel contentPanel;
    private final JPanel buttonPanel;
    private Card selected = null;
    private JButton exit = new JButton();

    public NotificationPanel(GamePlayer board) {
        super();
        this.gamePlayer = board;
        this.setLayout(new GridLayout(2, 1));

        contentPanel = new JPanel();
        contentPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        exit.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        exit.setBackground(Color.GRAY);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
            }
        });

        buttonPanel.add(exit);
    }

    public void seeTheFuture(List<Card> future) {
        initializePane();

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            contentPanel.add(futureCard);
        }
        exit.setText("Done");
        gamePlayer.updateDisplay();
    }

    public void alterTheFuture(List<Card> future) {
        initializePane();

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            futureCard.addActionListener(new ActionListener() {
                private Card card = topCard;
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (selected != null) {
                        int current = cardOrder.indexOf(card);
                        int swap = cardOrder.indexOf(selected);
                        cardOrder.set(swap, card);
                        cardOrder.set(current, selected);
                        selected = null;
                        alterTheFuture(new ArrayList<>(cardOrder));
                    } else {
                        selected = card;
                    }
                }
            });
            contentPanel.add(futureCard);
        }

        exit.setText("Done");
        gamePlayer.updateDisplay();
    }

    public void notifyPlayers(String ContentMessage, String doneMessage) {
        initializePane();

        JLabel content = new JLabel("<html><center><br>" +
                ContentMessage + "<br><br></center></html>");

        contentPanel.add(content);
        content.setOpaque(true);
        content.setBackground(Color.CYAN);

        exit.setText(doneMessage);
        gamePlayer.updateDisplay();
    }

    private void initializePane() {
        contentPanel.removeAll();
        if (!this.isAncestorOf(contentPanel)) {
            this.add(contentPanel);
        }
        if (!this.isAncestorOf(buttonPanel)) {
            this.add(buttonPanel);
        }
        cardOrder.clear();
    }

    @Override
    public void removeAll() {
        super.removeAll();
        gamePlayer.updateDisplay();
        if (cardOrder.size() > 0) {
            gamePlayer.returnFutureCards(cardOrder);
            cardOrder.clear();
        }
        selected = null;
    }
}

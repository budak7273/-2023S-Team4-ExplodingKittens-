package presentation;

import system.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationPanel extends JPanel {
    private ArrayList<Card> cardOrder = new ArrayList<>();;
    private Card selected = null;
    private JButton exit;
    private GamePlayer gamePlayer;
    private JPanel cardPanel;
    final int button_width = 150;
    final int button_height = 50;

    public NotificationPanel(GamePlayer board) {
        super();
        this.gamePlayer = board;
        this.setLayout(new GridLayout(2, 1));
        cardPanel = new JPanel();
        cardPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    }

    public void seeTheFuture(List<Card> future) {
        cardPanel.removeAll();
        if (cardPanel != null && !this.isAncestorOf(cardPanel)) {
            this.add(cardPanel);
        }

        cardOrder.clear();

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            cardPanel.add(futureCard);
        }

        if (!this.isAncestorOf(exit)) {
            this.generateExitButton("Done");
        }
        gamePlayer.updateDisplay();
    }

    public void alterTheFuture(List<Card> future) {
        cardPanel.removeAll();
        if (cardPanel != null && !this.isAncestorOf(cardPanel)) {
            this.add(cardPanel);
        }
        cardOrder.clear();

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            futureCard.addActionListener(new ActionListener() {
                Card card = topCard;
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
            cardPanel.add(futureCard);
        }

        if (!this.isAncestorOf(exit)) {
            this.generateExitButton("Done");
        }
        gamePlayer.updateDisplay();
    }

    private void generateExitButton(String message) {
        exit = new JButton(
                "<html><center>" + message + "</center></html>");
        exit.setPreferredSize(new Dimension(button_width, button_height));
        exit.setBackground(Color.GRAY);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
            }
        });
        JPanel close = new JPanel();
        close.setLayout(new FlowLayout());
        close.add(exit);
        this.add(close);
    }

    @Override
    public void removeAll() {
        super.removeAll();
        if (cardOrder.size() > 0) {
            gamePlayer.returnFutureCards(cardOrder);
            cardOrder.clear();
        }
        selected = null;
    }
}

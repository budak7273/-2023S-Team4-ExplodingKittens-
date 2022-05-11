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
    private ArrayList<Card> cardOrder;
    private JButton exit;
    private GamePlayer gamePlayer;
    final int button_width = 150;
    final int button_height = 50;

    public NotificationPanel(GamePlayer board) {
        super();
        this.gamePlayer = board;
        this.setLayout(new GridLayout(2, 1));
        cardOrder = new ArrayList<>();
    }

    public void seeTheFuture(List<Card> future) {
        JPanel futureCardPanel = new JPanel();
        futureCardPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        JPanel futureClose = new JPanel();
        futureClose.setLayout(new FlowLayout());

        for (int i = 0; i < future.size(); i++) {
            Card topCard = future.get(i);
            JButton futureCard = gamePlayer.createCardImage(
                    topCard.getName(), i + "");
            cardOrder.add(topCard);
            futureCardPanel.add(futureCard);
        }
        this.generateExitButton("Done");
        futureClose.add(exit);

        this.add(futureCardPanel);
        this.add(futureClose);
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
    }

    @Override
    public void removeAll() {
        super.removeAll();
        if (cardOrder.size() > 0) {
            gamePlayer.returnFutureCards(cardOrder);
            cardOrder.clear();
        }
    }
}

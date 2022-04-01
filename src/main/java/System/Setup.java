package System;

import java.util.*;

public class Setup {
    public Queue<User> createUsers(List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }

        if (names.size() < 2 || names.size() > 10) {
            throw new IllegalArgumentException();
        }

        Set<String> checkDuplicateSet = new HashSet<String>(names);
        if (checkDuplicateSet.size() != names.size()) {
            throw new IllegalArgumentException();
        }


        Queue<User> queue = new LinkedList<User>();
        for (String name : names) {
            User user = new User();
            queue.add(user);
        }
        return queue;
    }

    public DrawDeck createDrawDeck(List<String> cardNames) {
        if (cardNames.size() < 53) {
            throw new IllegalArgumentException();
        }
        DrawDeck drawDeck = new DrawDeck();
        for (String name : cardNames) {
            Card card = new Card();
            drawDeck.addCard(card);
        }
        return drawDeck;
    }
}

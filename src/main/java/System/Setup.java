package System;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Setup {
    public Queue<User> createUsers(List<String> names) {
        if (names == null) {
            throw new NullPointerException();
        }

        if (names.size() < 2 || names.size() > 10) {
            throw new IllegalArgumentException();
        }

        Set<String> checkDuplicateSet = new HashSet<>(names);
        if (checkDuplicateSet.size() != names.size()) {
            throw new IllegalArgumentException();
        }


        Queue<User> queue = new LinkedList<>();
        for (String name : names) {
            User user = new User();
            queue.add(user);
        }
        return queue;
    }

    public DrawDeck createDrawDeck(File cardInfoFile) {
        try {
            Scanner cardInfoScanner = new Scanner(cardInfoFile);
            int numOfCardsUntilRequiredCountIsReached = 113;
            while (cardInfoScanner.hasNext()) {
                cardInfoScanner.next();
                numOfCardsUntilRequiredCountIsReached--;
            }
            if (numOfCardsUntilRequiredCountIsReached != 0) {
                throw new IllegalArgumentException("File does not match size of Party Pack deck");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

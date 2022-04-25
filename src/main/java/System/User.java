package System;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User {
    String name;
    Boolean alive = true;
    ArrayList<Card> hand = new ArrayList<>();

    public User(){
        this.name = "";
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, boolean alive, ArrayList<Card> hand) {
        this.name = name;
        this.alive = alive;
        this.hand = hand;
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHand() {
        return this.hand;
    }

    public void addCard(Card drawnCard) {
        this.hand.add(drawnCard);
    }
}

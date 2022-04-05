package System;


import java.util.ArrayList;

public class User {
    String name;
    Boolean alive;

    public User(){}

    public User(String name, boolean alive, ArrayList<Card> hand) {
        this.name = name;
        this.alive = alive;
    }
}

package System;


import java.util.ArrayList;

public class User {
    String name;

    public User(){}

    public User(String name, boolean alive, ArrayList<Card> hand) {
        this.name = name;
    }
}

package Presentation;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import System.*;
public class Gameboard {
    private Queue<User> users = new ArrayDeque<User>();
    private DrawDeck drawDeck = new DrawDeck();
    private DiscardDeck discardDeck = new DiscardDeck();
    public void createGame(Integer playerCount) throws InvalidPlayerCountException {
        if(playerCount == 1){
            throw new InvalidPlayerCountException("ERROR: Must have at least 2 players!");
        }
        List<String> usernames = readUserInfo();
        Setup s = new Setup(usernames.size());
        this.users = s.createUsers(usernames);
        this.drawDeck = s.createDrawDeck(new File(""));
        this.discardDeck = s.createDiscardDeck();

    }
    public List<String> readUserInfo(){
        List<String> twoUsers = new ArrayList<>();
        twoUsers.add("player1");
        twoUsers.add("player2");
        return twoUsers;
    }

    public Queue<User> getUsers() {
        return this.users;
    }

    public DrawDeck getDrawDeck() {
        return this.drawDeck;
    }

    public DiscardDeck getDiscardDeck() {
        return this.discardDeck;
    }
}

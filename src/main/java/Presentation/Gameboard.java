package Presentation;

import java.io.File;
import java.util.*;

import System.*;
public class Gameboard {
    private Queue<User> users = new ArrayDeque<>();
    private DrawDeck drawDeck = new DrawDeck();
    private DiscardDeck discardDeck = new DiscardDeck();
    Scanner scanner = new Scanner(System.in);

    public void createGame() throws InvalidPlayerCountException {

        List<String> usernames = readUserInfo();
        int playerCount = usernames.size();
        if(playerCount == 1){
            throw new InvalidPlayerCountException("ERROR: Must have at least 2 players!");
        }
        Setup s = new Setup(playerCount);
        this.users = s.createUsers(usernames);
       // this.drawDeck = s.createDrawDeck(new File(""));
        this.discardDeck = s.createDiscardDeck();

    }
    public List<String> readUserInfo(){
        List<String> userNameList = new ArrayList<>();
        System.out.println("Please enter player usernames!");
        int numberOfPlayers;

            while(scanner.hasNext()){
                String username = scanner.next();
                userNameList.add(username);
                System.out.println(username + " has been added to the game!");
                System.out.println("Would you like to add another player? (y/n)");
                String response = scanner.next();
                boolean addAnotherPlayer = (response.equals("Y") || response.equals("y"));
                if(!addAnotherPlayer){
                    break;
                } else{
                    System.out.println("Enter next player username");
                }
            }


        scanner.close();
        return userNameList;
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

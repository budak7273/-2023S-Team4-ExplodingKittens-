package System;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User {
    String name;
    Boolean alive;
    ArrayList<Card> hand;

    public User(){
        this.name = "";
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public User(String name, boolean alive, ArrayList<Card> hand) {
        this.name = name;
        this.alive = alive;
        this.hand = hand;
    }

    public List<Card> checkForPairs() {
        List<Card> result = new ArrayList<>();

        if (this.hand == null) {
            throw new IllegalArgumentException();
        }

        for (int i=0;i<hand.size();i++){
            for (int j=i;j<hand.size();j++){
                Card card1 = hand.get(i);
                Card card2 = hand.get(j);
                if(card1.getClass().equals(CatCard.class)
                        &&card2.getClass().equals(CatCard.class)){
                    CatType card1Type = ((CatCard) card1).type;
                    CatType card2Type = ((CatCard) card2).type;

                    if(card1Type==card2Type || card1Type==CatType.FERAL){
                        result.add(card1);
                    }
                    else if(card2Type==CatType.FERAL){
                        result.add(card2);
                    }
                }
            }
        }

        return result;
    }
}

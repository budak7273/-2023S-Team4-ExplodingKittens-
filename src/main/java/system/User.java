package system;


import datasource.CardType;

import java.util.*;

public class User {
    private String name;
    private Boolean alive = true;
    private ArrayList<Card> hand = new ArrayList<>();

    public User() {
        this.name = "";
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public User(final String playerName) {
        this.name = playerName;
    }

    public User(final String playerName,
                final boolean activeStatus,
                final ArrayList<Card> playerHand) {
        this.name = playerName;
        this.alive = activeStatus;
        this.hand = playerHand;
    }

    public String getName() {

        return this.name;
    }

    public List<Card> getHand() {

        return this.hand;
    }

    public void addCard(final Card drawnCard) {

        this.hand.add(drawnCard);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void die() {
        this.alive = false;
    }

    public boolean checkForSpecialEffectPotential() {
        int feralCount=0;
        ArrayList<Card> list = new ArrayList<>();
        for(Card card:this.hand){
            if(card.getType()== CardType.FERAL_CAT) feralCount++;
            else if(card.isCatCard()) list.add(card);
        }

        if((feralCount>=1&&list.size()>=1)||
        feralCount>=2) {return true;}
        else{
            if(list.size()<2){return false;}
            else {
                for(int i=0; i<list.size()-1;i++){
                    for(int j=i+1; j<list.size();j++){
                        if (list.get(i).getType()==list.get(j).getType()) return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean verifyEffectForCardsSelected(final List<Integer> selected) {
        if(selected==null){
            throw new IllegalArgumentException(
                    "Null list should never happens.");
        }
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            throw new IllegalArgumentException(
                    "You cannot select cards when your hand is empty.");
        }
        if (selected.size() > this.hand.size()){
            throw new IllegalArgumentException(
                    "You should never select more number of cards than what you have. Something is wrong.");
        }
        Set<Integer> set = new HashSet<>();
        set.addAll(selected);
        if (set.size() < selected.size()){
            throw new IllegalArgumentException(
                    "You cannot select card at multiple times.");
        }

        Card first = hand.get(selected.get(0));

        for(Integer i:selected){
            if(!hand.get(i).isCatCard()) return false;
            CardType type1 = first.getType();
            CardType type2 = hand.get(i).getType();
            if(type1!=type2 && type1!=CardType.FERAL_CAT && type2 !=CardType.FERAL_CAT) return false;
        }

        return true;
    }
}

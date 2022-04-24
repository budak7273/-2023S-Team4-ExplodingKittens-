package presentation;

public class ExplodingKittens {
    public static void main(String[] args){
        Gameboard g = new Gameboard();
        try {
            g.createGame();
        } catch(InvalidPlayerCountException e){
            System.out.println("invalid player count, please try again. Must have 2-10 players. ");
        }
    }
}

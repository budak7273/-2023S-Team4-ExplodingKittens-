package Presentation;

public class Gameboard {
    public void createGame(Integer playerCount) throws InvalidPlayerCountException {
        if(playerCount == 1){
            throw new InvalidPlayerCountException("ERROR: Must have at least 2 players!");
        }
    }
}

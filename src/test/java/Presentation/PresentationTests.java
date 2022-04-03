package Presentation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PresentationTests {
    @Test
    public void createGame1PlayerTest()  {
        Gameboard gameboard = new Gameboard();
        Integer onePlayer = 1;
        Throwable exception = assertThrows(InvalidPlayerCountException.class,  () -> gameboard.createGame(onePlayer));
        assertEquals("ERROR: Must have at least 2 players!", exception.getMessage());
    }


}

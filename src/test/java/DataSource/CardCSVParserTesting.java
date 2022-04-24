package DataSource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CardCSVParserTesting {
    @Test
    public void testGenerateListOfCards_withEmptyInput() {
        String path = "src/test/resources/empty.csv";
        CardCSVParser parser = new CardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCards_withTooFewCards() {
        String path = "src/test/resources/fullfile_minusone.csv";
        CardCSVParser parser = new CardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCards_withDataMissing() {
        String path = "src/test/resources/fullfile_datamissing.csv";
        CardCSVParser parser = new CardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }
}

package datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.Card;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CardCSVParserTesting {

    private static final int PARTY_PACK_SIZE = 101;
    private static final int PARTY_PACK_PAW_ONLY_SIZE = 41;

    @Test
    public void testGenerateListOfCards_withEmptyInput() {
        String path = "src/test/resources/empty.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCards_withTooFewCards() {
        String path = "src/test/resources/fullfile_minusone.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCards_withDataMissing() {
        String path = "src/test/resources/fullfile_datamissing.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCards_withDataInvalid() {
        String path = "src/test/resources/fullfile_invalid.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCards_None() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(false, false);
        Assertions.assertTrue(cardList.isEmpty());
    }

    private boolean cardListContainsAllTypesOfRegularCards(List<Card> cardList) {
        CardType[] cardTypes = CardType.class.getEnumConstants();
        for (CardType cardType : cardTypes) {
            if (cardType != CardType.DEFUSE && cardType != CardType.EXPLODING_KITTEN) {
                if (!cardList.stream().anyMatch(card -> card.getType() == cardType)) {
                    return false;
                };
            }
        }
        return true;
    }

    @Test
    public void testGenerateListOfCards_withPawOnly() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(true, false);
        Assertions.assertTrue(cardList.size() == PARTY_PACK_PAW_ONLY_SIZE);

        Assertions.assertTrue(cardListContainsAllTypesOfRegularCards(cardList));
    }


    @Test
    public void testGenerateListOfCards_withNoPawOnly() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(false, true);
        Assertions.assertTrue(cardList.size() == PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE);

        Assertions.assertTrue(cardListContainsAllTypesOfRegularCards(cardList));
    }

    @Test
    public void testGenerateListOfCards_All() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(true, true);
        Assertions.assertTrue(cardList.size() == PARTY_PACK_SIZE);

        Assertions.assertTrue(cardListContainsAllTypesOfRegularCards(cardList));
    }

}

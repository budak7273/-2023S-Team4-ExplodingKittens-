package datasource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.Card;

import java.io.File;
import java.util.List;
import java.util.function.Predicate;

public class CardCSVParserTesting {

    private static final int PARTY_PACK_SIZE = 101;
    private static final int PARTY_PACK_PAW_ONLY_SIZE = 41;

    @Test
    public void testGenerateListOfCardsWithEmptyInput() {
        String path = "src/test/resources/empty.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsWithTooFewCards() {
        String path = "src/test/resources/fullfile_minusone.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsWithDataMissing() {
        String path = "src/test/resources/fullfile_datamissing.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsWithDataInvalid() {
        String path = "src/test/resources/fullfile_invalid.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsNone() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(false, false);
        Assertions.assertTrue(cardList.isEmpty());
    }

    private boolean cardListContainsAllRegularTypes(final List<Card> cardList) {
        CardType[] cardTypes = CardType.class.getEnumConstants();
        for (CardType cardType : cardTypes) {
            boolean typeIsDefuse = cardType == CardType.DEFUSE;
            boolean typeIsExploding = cardType == CardType.EXPLODING_KITTEN;

            if (!typeIsDefuse && !typeIsExploding) {
                Predicate<Card> cardMatchesType =
                        card -> card.getType() == cardType;

                if (!cardList.stream().anyMatch(cardMatchesType)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void testGenerateListOfCardsWithPawOnly() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(true, false);
        Assertions.assertTrue(cardList.size() == PARTY_PACK_PAW_ONLY_SIZE);

        Assertions.assertTrue(cardListContainsAllRegularTypes(cardList));
    }


    @Test
    public void testGenerateListOfCardsWithNoPawOnly() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(false, true);

        int expectedSize = PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE;
        Assertions.assertEquals(expectedSize, cardList.size());
        Assertions.assertTrue(cardListContainsAllRegularTypes(cardList));
    }

    @Test
    public void testGenerateListOfCardsAll() {
        String path = "src/test/resources/fullfile.csv";
        File csvFile = new File(path);
        CardCSVParser parser = new CardCSVParser(csvFile);
        List<Card> cardList = parser.generateListOfCards(true, true);
        Assertions.assertTrue(cardList.size() == PARTY_PACK_SIZE);

        Assertions.assertTrue(cardListContainsAllRegularTypes(cardList));
    }

}

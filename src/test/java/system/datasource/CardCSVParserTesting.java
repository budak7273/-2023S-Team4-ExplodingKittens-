package system.datasource;

import datasource.CardCSVParser;
import datasource.CardType;
import datasource.ResourceHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.Card;

import java.io.InputStream;
import java.util.List;
import java.util.function.Predicate;

class CardCSVParserTesting {

    private static final int PARTY_PACK_SIZE = 107;
    private static final int PARTY_PACK_PAW_ONLY_SIZE = 44;

    CardCSVParser createCardCSVParser(String path) {
        InputStream csvFile;
        csvFile = ResourceHelper.getAsStream(path);
        System.out.println(csvFile);

        return new CardCSVParser(csvFile);
    }

    @Test
    public void testGenerateListOfCardsWithEmptyInput() {
        String path = "/testingcsvs/empty.csv";
        CardCSVParser parser = createCardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsWithTooFewCards() {
        String path = "/testingcsvs/fullfile_minusone.csv";
        CardCSVParser parser = createCardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsWithDataMissing() {
        String path = "/testingcsvs/fullfile_datamissing.csv";
        CardCSVParser parser = createCardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsWithDataInvalid() {
        String path = "/testingcsvs/fullfile_invalid.csv";
        CardCSVParser parser = createCardCSVParser(path);
        Executable executable = () -> parser.generateListOfCards(true, true);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testGenerateListOfCardsNone() {
        String path = "/testingcsvs/fullfile.csv";
        CardCSVParser parser = createCardCSVParser(path);
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
        String path = "/testingcsvs/fullfile.csv";
        CardCSVParser parser = createCardCSVParser(path);
        List<Card> cardList = parser.generateListOfCards(true, false);

        Assertions.assertTrue(cardList.size() == PARTY_PACK_PAW_ONLY_SIZE);
        Assertions.assertTrue(cardListContainsAllRegularTypes(cardList));
    }


    @Test
    public void testGenerateListOfCardsWithNoPawOnly() {
        String path = "/testingcsvs/fullfile.csv";
        CardCSVParser parser = createCardCSVParser(path);
        List<Card> cardList = parser.generateListOfCards(false, true);

        int expectedSize = PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE;
        Assertions.assertEquals(expectedSize, cardList.size());
        Assertions.assertTrue(cardListContainsAllRegularTypes(cardList));
    }

    @Test
    public void testGenerateListOfCardsAll() {
        String path = "/testingcsvs/fullfile.csv";
        CardCSVParser parser = createCardCSVParser(path);
        List<Card> cardList = parser.generateListOfCards(true, true);

        Assertions.assertTrue(cardList.size() == PARTY_PACK_SIZE);
        Assertions.assertTrue(cardListContainsAllRegularTypes(cardList));
    }
}

package datasource;

import system.Card;
import system.AttackCard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CardCSVParser {

    private File csvFile;
    private final int maxCardCount = 120;
    public CardCSVParser(final File csv) {

        this.csvFile = csv;
    }

    public List<Card> generateListOfCards(final boolean includePaw,
                                          final boolean includeNoPaw) {
        verifyCSVFormat();

        List<Card> cardList = new ArrayList<Card>();
        Scanner scanner = generateScanner();
        String header = scanner.nextLine();
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            // TODO: it's using attackCard() as dummy here.
            //  Fix it with necessary cards.
            if (includePaw
                    && Boolean.parseBoolean(cardProperties[1])) {
                cardList.add(new AttackCard());
            }
            if (includeNoPaw
                    && !Boolean.parseBoolean(cardProperties[1])) {
                cardList.add(new AttackCard());
            }
        }

        return cardList;
    }

    private void verifyCSVFormat() {
        Scanner scanner = generateScanner();
        String header = scanner.nextLine();
        int cardCount = 0;
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            if (cardProperties.length != 2) {
                throw new IllegalArgumentException(
                        "Missing data in .csv file.");
            }
            String cardType = cardProperties[0];
            final int typeCount = 17;
            String[] types = new String[typeCount];
            int i = 0;
            for (Enum typeEnum : CardType.class.getEnumConstants()) {
                types[i] = typeEnum.toString();
                i++;
            }
            if (Arrays.stream(types).noneMatch(cardType::equals)) {
                throw new IllegalArgumentException("Invalid card type "
                        + cardType + " found in file");
            }

            cardCount++;
        }
        if (cardCount != maxCardCount) {
            throw new IllegalArgumentException("Bad number "
                    + "of cards in .csv file.");
        }
    }

    private Scanner generateScanner() {
        try {
            return new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                    "Could not generate scanner from file.");
        }
    }
}

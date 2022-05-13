package datasource;

import system.Card;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CardCSVParser {

    private File csvFile;
    private static final int MAX_CARD_COUNT = 101;

    public CardCSVParser(File csv) {
        this.csvFile = csv;
    }

    public List<Card> generateListOfCards(boolean includePaw,
                                          boolean includeNoPaw) {
        verifyCSVFormat();

        List<Card> cardList = new ArrayList<Card>();
        Scanner scanner = generateScanner();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            String cardTypeName = cardProperties[0];

            CardType cardType = CardType.valueOf(cardTypeName);
            Card card = new Card(cardType);

            if (includePaw
                    && Boolean.parseBoolean(cardProperties[1])) {
                cardList.add(card);
            }
            if (includeNoPaw
                    && !Boolean.parseBoolean(cardProperties[1])) {
                cardList.add(card);
            }
        }

        return cardList;
    }

    private void verifyCSVFormat() {
        Scanner scanner = generateScanner();
        scanner.nextLine();
        int cardCount = 0;
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            if (cardProperties.length != 2) {
                throw new IllegalArgumentException(
                        Messages.getMessage(Messages.MISSING_DATA));
            }
            String cardType = cardProperties[0];
            final int typeCount = 17;
            String[] types = new String[typeCount];
            int i = 0;
            for (CardType typeEnum : CardType.class.getEnumConstants()) {
                types[i] = typeEnum.name();
                i++;
            }
            if (Arrays.stream(types).noneMatch(cardType::equals)) {
                throw new IllegalArgumentException(Messages
                        .getMessage(Messages.INVALID_CARD_TYPE) + cardType
                        + Messages.getMessage(Messages.FOUND_IN_FILE));
            }

            cardCount++;
        }
        if (cardCount != MAX_CARD_COUNT) {
            throw new IllegalArgumentException(Messages
                    .getMessage(Messages.BAD_NUMBER_OF_CARDS));

        }
    }

    private Scanner generateScanner() {
        try {
            return new Scanner(csvFile, "UTF-8");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                    Messages.getMessage(Messages.COULD_NOT_GENERATE));
        }
    }
}

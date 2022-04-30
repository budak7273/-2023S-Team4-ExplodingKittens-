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
    private final int maxCardCount = 101;

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
            String cardTypeName = cardProperties[0];

            CardType cardType = CardType.valueOf(cardTypeName);
            Card card = CardFactory.createCardOfType(cardType);

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
        String header = scanner.nextLine();
        int cardCount = 0;
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            if (cardProperties.length != 2) {
                throw new IllegalArgumentException(
                        Messages.getMessage("MissingDataMessage"));
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
                throw new IllegalArgumentException(Messages.getMessage("InvalidCardTypeMessage")
                        + cardType + Messages.getMessage("FoundInFileMessage"));
            }

            cardCount++;
        }
        if (cardCount != maxCardCount) {
            throw new IllegalArgumentException(Messages.getMessage("BadNumberOfCardsMessage"));
        }
    }

    private Scanner generateScanner() {
        try {
            return new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                    Messages.getMessage("CouldNotGenerateMessage"));
        }
    }
}

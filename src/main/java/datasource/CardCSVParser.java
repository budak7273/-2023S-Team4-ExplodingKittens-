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
    private Card card;

    public CardCSVParser(File csv) {
        this.csvFile = csv;
    }

    private Card createCardFromTypeName(String cardTypeName){
        CardType cardType = CardType.valueOf(cardTypeName);
        return new Card(cardType);
    }

    public List<Card> generateListOfCards(boolean includePaw, boolean includePawless){
        return generateListOfCardsWithVerification(includePaw, includePawless);
    }

    public List<Card> generateListOfCardsWithVerification(boolean includePaw, boolean includePawless){
        List<Card> cardList = new ArrayList<Card>();
        int cardCount = 0;

        Scanner scanner = generateScanner();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");

            checkPropertyLength(cardProperties);

            String cardTypeName = cardProperties[0];
            Boolean hasAPaw = Boolean.parseBoolean(cardProperties[1]);

            final int typeCount = 17;
            String[] types = new String[typeCount];
            int i = 0;
            for (CardType typeEnum : CardType.class.getEnumConstants()) {
                types[i] = typeEnum.name();
                i++;
            }
            if (Arrays.stream(types).noneMatch(cardTypeName::equals)) {
                throw new IllegalArgumentException(Messages
                        .getMessage(Messages.INVALID_CARD_TYPE) + cardTypeName
                        + Messages.getMessage(Messages.FOUND_IN_FILE));
            }
            cardCount++;

            Card currentCard = createCardFromTypeName(cardTypeName);
            if(includePaw && hasAPaw){
                cardList.add(currentCard);
            } else if (includePawless && !hasAPaw) {
                cardList.add(currentCard);
            }
        }
        checkCardCount(cardCount);
        return cardList;
        }
    private Scanner generateScanner() {
        try {
            return new Scanner(csvFile, "UTF-8");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(
                    Messages.getMessage(Messages.COULD_NOT_GENERATE));
        }
    }
    private void checkPropertyLength(String[] cardProperties){
        if (cardProperties.length != 2) {
            throw new IllegalArgumentException(
                    Messages.getMessage(Messages.MISSING_DATA));
        }
    }

    private void checkCardCount(int cardCount){
        if (cardCount != MAX_CARD_COUNT) {
            throw new IllegalArgumentException(Messages
                    .getMessage(Messages.BAD_NUMBER_OF_CARDS));
        }
    }
}

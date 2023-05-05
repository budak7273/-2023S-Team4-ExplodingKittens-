package datasource;

import system.Card;

import javax.swing.Icon;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CardCSVParser {

    private static final int MAX_CARD_COUNT = 107;
    private final InputStream csvFile;

    public CardCSVParser(InputStream csv) {
        this.csvFile = csv;
    }

    private Card createCardFromTypeNameAndIcon(String cardTypeName, Icon icon) {
        CardType cardType = CardType.valueOf(cardTypeName);
        return new Card(cardType, icon);
    }

    public List<Card> generateListOfCards(boolean includePaw, boolean includePawless) {
        return generateListOfCardsWithVerification(includePaw, includePawless);
    }

    public List<Card> generateListOfCardsWithVerification(boolean includePaw, boolean includePawless) {
        List<Card> cardList = new ArrayList<>();
        int cardCount = 0;

        Scanner scanner = generateScanner();
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");

            verifyPropertyLength(cardProperties);

            String cardTypeName = cardProperties[0];
            Boolean hasAPaw = Boolean.parseBoolean(cardProperties[1]);

            String iconLocation = cardProperties[2];
            Icon icon = ResourceHelper.getAsImageIcon(iconLocation);

            verifyCardType(cardTypeName);
            cardCount++;

            Card currentCard = createCardFromTypeNameAndIcon(cardTypeName, icon);
            if (includePaw && hasAPaw) {
                cardList.add(currentCard);
            } else if (includePawless && !hasAPaw) {
                cardList.add(currentCard);
            }
        }
        verifyCardCount(cardCount);
        return cardList;
    }

    private Scanner generateScanner() {
        return new Scanner(csvFile, "UTF-8");
    }

    private void verifyPropertyLength(String[] cardProperties) {
        final int fields = 3;
        if (cardProperties.length != fields) {
            throw new IllegalArgumentException(
                    I18n.getMessage("MissingDataMessage"));
        }
    }

    private void verifyCardCount(int cardCount) {
        if (cardCount != MAX_CARD_COUNT) {
            throw new IllegalArgumentException(I18n
                                                       .getMessage("BadNumberOfCardsMessage"));
        }
    }

    private void verifyCardType(String cardTypeName) {
        Set<String> allCardTypes = Collections.unmodifiableSet(new HashSet<>(CardType.ENUM_VALUES));
        if (!allCardTypes.contains(cardTypeName)) {
            throw new IllegalArgumentException(I18n
                                                       .getMessage("InvalidCardTypeMessage") + cardTypeName
                                               + I18n.getMessage("FoundInFileMessage"));
        }
    }
}

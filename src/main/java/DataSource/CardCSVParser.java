package DataSource;

import System.Card;
import System.AttackCard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardCSVParser {

    File csvFile;

    public CardCSVParser(File csvFile) {
        this.csvFile = csvFile;
    }

    public List<Card> generateListOfCards(boolean includePaw, boolean includeNoPaw) {
        verifyCSVFormat();

        List<Card> cardList = new ArrayList<Card>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String header = scanner.nextLine();
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            // TODO: it's using attackCard() as dummy here. Fix it with necessary cards.
            if (includePaw && Boolean.parseBoolean(cardProperties[1]) == true) {
                cardList.add(new AttackCard());
            }
            if (includeNoPaw && Boolean.parseBoolean(cardProperties[1]) == false) {
                cardList.add(new AttackCard());
            }
        }
        return cardList;
    }

    private void verifyCSVFormat() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String header = scanner.nextLine();
        int cardCount = 0;
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            if (cardProperties.length != 2) {
                throw new IllegalArgumentException("Missing data in .csv file.");
            }
            cardCount++;
        }
        System.out.println(cardCount);
        if (cardCount != 120) {
            throw new IllegalArgumentException("Bad number of cards in .csv file.");
        }
    }
}

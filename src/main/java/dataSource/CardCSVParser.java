package dataSource;

import system.Card;
import system.AttackCard;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
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
        Scanner scanner = generateScanner();
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
        Scanner scanner = generateScanner();
        String header = scanner.nextLine();
        int cardCount = 0;
        while (scanner.hasNextLine()) {
            String cardInfo = scanner.nextLine();
            String[] cardProperties = cardInfo.split(",");
            if (cardProperties.length != 2) {
                throw new IllegalArgumentException("Missing data in .csv file.");
            }

            // TODO: move this logic to an enum class
            String cardType = cardProperties[0];
            String[] validTypes = new String[] {"Attack", "Exploding Kitten", "Defuse", "Skip", "Favor", "Shuffle",
            "Beard Cat", "Tacocat", "Hairy Potato Cat", "Rainbow-Ralphing Cat", "Cattermelon", "Feral Cat",
            "Draw From The Bottom", "Nope", "Alter The Future", "Targeted Attack", "See The Future"};
            if (!Arrays.stream(validTypes).anyMatch(cardType::equals)) {
                throw new IllegalArgumentException("Invalid card type " + cardType + " found in file");
            }

            cardCount++;
        }
        if (cardCount != 120) {
            throw new IllegalArgumentException("Bad number of cards in .csv file.");
        }
    }

    private Scanner generateScanner() {
        try {
            return new Scanner(csvFile);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Could not generate scanner from file.");
        }
    }
}

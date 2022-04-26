package system;

public abstract class Card {
    private final String cardName;

    public Card(final String name) {
        this.cardName = name;
    }

    public String getName() {
        return this.cardName;
    }
}

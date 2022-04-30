package datasource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum Messages {
    NOT_ENOUGH_PLAYERS("NotEnoughPlayersMessage"),
    ENTER_PLAYER_1_NAME("EnterPlayer1NameMessage"),
    PLAYER_ADDED_TO_GAME("PlayerAddedToGameMessage"),
    ADD_ANOTHER_PLAYER("AddAnotherPlayerMessage"),
    ENTER_PLAYER("EnterPlayerMessage"),
    PLAYER_USERNAME("PlayerUsernameMessage"),
    START_GAME("StartGameMessage"),
    YOUR_TURN("StartGameMessage"),
    MISSING_DATA("MissingDataMessage"),
    INVALID_CARD_TYPE("InvalidCardTypeMessage"),
    FOUND_IN_FILE("FoundInFileMessage"),
    BAD_NUMBER_OF_CARDS("BadNumberOfCardsMessage"),
    COULD_NOT_GENERATE("CouldNotGenerateMessage"),
    COULD_NOT_CREATE("CouldNotCreateMessage"),
    ILLEGAL_PLAYERS("IllegalPlayersMessage"),
    EMPTY_DRAW_DECK("EmptyDrawDeckMessage"),
    EMPTY_HAND("EmptyHandMessage");

    private static Locale currentLocation = Locale.GERMAN;
    private final String displayName;

    Messages(final String displayNameStr) {
        this.displayName = displayNameStr;
    }

    public static String getMessage(Messages message) {
        return getMessage(message.toString());
    }

    @Override
    public String toString() {
        return this.displayName;
    }

    public static String getMessage(final String key) {

        ResourceBundle messages =
                ResourceBundle.getBundle("message", currentLocation);
        String value = messages.getString(key);
        return value;
    }
}

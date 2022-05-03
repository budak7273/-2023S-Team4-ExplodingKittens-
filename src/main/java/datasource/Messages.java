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
    EMPTY_HAND("EmptyHandMessage"),
    BAD_CARD_SELECTION("BadCardSelectionMessage");

    private static Locale currentLocation = Locale.GERMAN;
    private String messageName;

    Messages(String displayNameStr) {
        this.messageName = displayNameStr;
    }

    public static String getMessage(Messages message) {
        return getMessage(message.toString());
    }

    @Override
    public String toString() {
        return this.messageName;
    }

    private static String getMessage(String key) {

        ResourceBundle messages =
                ResourceBundle.getBundle("message", currentLocation);
        String value = messages.getString(key);
        return value;
    }
}

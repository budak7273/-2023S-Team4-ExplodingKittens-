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
    YOUR_TURN("YourTurnMessage"),
    MISSING_DATA("MissingDataMessage"),
    INVALID_CARD_TYPE("InvalidCardTypeMessage"),
    FOUND_IN_FILE("FoundInFileMessage"),
    BAD_NUMBER_OF_CARDS("BadNumberOfCardsMessage"),
    COULD_NOT_GENERATE("CouldNotGenerateMessage"),
    COULD_NOT_CREATE("CouldNotCreateCardMessage"),
    ILLEGAL_PLAYERS("IllegalPlayersMessage"),
    EMPTY_DRAW_DECK("EmptyDrawDeckMessage"),
    EMPTY_HAND("EmptyHandMessage"),
    BAD_CARD_SELECTION("BadCardSelectionMessage"),
    CHOOSE_LANGUAGE("ChooseLanguageMessage"),
    SWITCH_TO_CAT_MODE("SwitchToCatModeMessage"),
    SWITCH_TO_NORMAL_MODE("SwitchToNormalModeMessage"),
    WRONG_SELECTION_NORMAL_MODE("WrongSelectionNormalModeMessage"),
    CAT_SELECTION_NORMAL_MODE("CatSelectionNormalModeMessage"),
    ATTACK_CARD("AttackCard"),
    EXPLODING_KITTEN_CARD("ExplodingKitten"),
    DEFUSE_CARD("Defuse"),
    SKIP_CARD("Skip"),
    FAVOR_CARD("Favor"),
    SHUFFLE_CARD("Shuffle"),
    BEARD_CAT_CARD("BeardCat"),
    TACO_CAT_CARD("TacoCat"),
    HAIRY_POTATO_CAT("HairyPotatoCat"),
    RAINBOW_RALPHING_CAT("RainbowRalphingCat"),
    CATTERMELON("Cattermelon"),
    FERAL_CAT("FeralCat"),
    DRAW_FROM_THE_BOTTOM("DrawFromTheBottom"),
    NOPE("Nope"),
    ALTER_THE_FUTURE("AlterTheFuture"),
    TARGETED_ATTACK("TargetedAttack"),
    SEE_THE_FUTURE("SeeTheFuture"),
    ATTACK_DESC("AttackDesc"),
    EXPLODING_DESC("ExplodingDesc"),
    DEFUSE_DESC("DefuseDesc"),
    SKIP_DESC("SkipDesc"),
    FAVOR_DESC("FavorDesc"),
    SHUFFLE_DESC("ShuffleDesc"),
    BEARD_CAT_DESC("BeardCatDesc"),
    TACO_CAT_DESC("TacoCatDesc"),
    HAIRY_POTATO_CAT_DESC("HairyPotatoDesc"),
    RAINBOW_CAT_DESC("RainbowDesc"),
    CATTERMELON_DESC("CattermelonDesc"),
    FERAL_CAT_DESC("FeralCatDesc"),
    DRAW_FROM_BOTTOM_DESC("DrawFromBottomDesc"),
    NOPE_DESC("NopeDesc"),
    ALTER_DESC("AlterDesc"),
    TARGETED_ATTACK_DESC("TargetedAttackDesc"),
    SEE_DESC("SeeTheFutureDesc"),
    PLAYER_LOST_DEFUSE("PlayerLostDefuse"),
    PLAYER_DIED("PlayerDied"),
    EMPTY_DESC("Empty"),
    SWITCH_TO_SHOW_MODE("SwitchToShowModeMessage"),
    SWITCH_TO_HIDE_MODE("SwitchToHideModeMessage"),
    DUPLICATED_USERNAME("DuplicatedUserName"),
    NO_MUSIC("CouldntPlayMusic"),
    NOPE_STATUS_MESSAGE("NopeStatusMessage"),
    NOPE_STATUS_MESSAGE_NOT("NopeStatusMessageNot"),
    WRONG_INDEX_ENTERED("WrongIndexMessage"),
    WRONG_SELECTION_CAT_MODE("WrongSelectionCatModeMessage"),
    TOP_CARD("TopCard"),
    DRAW_DECK("DrawDeck"),
    CONFIRM("Confirm"),
    WARNING("Warning"),
    RIP("Rip"),
    VALID_INDEX("ValidIndex"),
    STEALING("Stealing"),
    DONE("Done"),
    LOCATION("Location"),
    PLACE_KITTEN("PlaceKitten"),
    KITTEN_PLACED("KittenPlaced"),
    WINNER_MESSAGE("WinnerMessage");
    private static Locale currentLocation = Locale.ENGLISH;
    private String messageName;

    Messages(String displayNameStr) {
        this.messageName = displayNameStr;
    }

    public static String getMessage(final Messages message) {
        return getMessageFromString(message.toString());
    }

    public static void switchLanguageToGerman() {
        currentLocation = Locale.GERMAN;
    }
    public static void switchLanguage(String language) {
        switch (language) {
            case "g": currentLocation = Locale.GERMAN; break;
            case "e": currentLocation = Locale.ENGLISH; break;
            default: System.out.println(
                    "invalid selection! defaulting to English. ");
        }
    }

    @Override
    public String toString() {
        return this.messageName;
    }

    private static String getMessageFromString(String key) {
        ResourceBundle messages =
                ResourceBundle.getBundle("message", currentLocation);
        String value = messages.getString(key);
        return value;
    }
}

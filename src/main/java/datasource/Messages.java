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
    EMPTY_DESC("Empty");

    private static Locale currentLocation = Locale.ENGLISH;
    private String messageName;

    Messages(String displayNameStr) {
        this.messageName = displayNameStr;
    }

    public static String getMessage(final Messages message) {
        return getMessage(message.toString());
    }

    public static void switchLanguageToGerman() {
        currentLocation = Locale.GERMAN;
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

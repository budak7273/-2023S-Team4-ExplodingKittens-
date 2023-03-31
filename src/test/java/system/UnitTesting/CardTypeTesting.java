package system.UnitTesting;

import datasource.CardType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTypeTesting {

    private List<CardType> types;

    void assertCatCardStatus(boolean isCatCard) {
        for (CardType type : types) {
            String message = "Card type '" + type + "' isCatCard is supposed to be " + isCatCard;
            assertEquals(isCatCard, type.getIsCatCard(), message);
        }
    }

    @Test
    void testCatCardsAreMarked() {
        types = Arrays.asList(CardType.BEARD_CAT, CardType.TACO_CAT, CardType.HAIRY_POTATO_CAT,
                              CardType.RAINBOW_RALPHING_CAT, CardType.CATTERMELON, CardType.FERAL_CAT);
        assertCatCardStatus(true);
    }

    @Test
    void testNonCatCardsAreNotMarked() {
        types = Arrays.asList(CardType.ATTACK, CardType.EXPLODING_KITTEN, CardType.DEFUSE, CardType.SKIP,
                              CardType.FAVOR, CardType.SHUFFLE, CardType.DRAW_FROM_THE_BOTTOM, CardType.NOPE,
                              CardType.ALTER_THE_FUTURE, CardType.TARGETED_ATTACK, CardType.SEE_THE_FUTURE);
        assertCatCardStatus(false);
    }
}

package datasource;

import system.Utils;
import system.cardEffects.*;

import java.util.List;

public enum CardType {
    ATTACK("AttackCard", new AttackEffect(), "AttackDesc"),
    EXPLODING_KITTEN("ExplodingKitten", null, "ExplodingDesc"),
    DEFUSE("Defuse", null, "DefuseDesc"),
    SKIP("Skip", new SkipEffect(), "SkipDesc"),
    FAVOR("Favor", new FavorEffect(), "SkipDesc"),
    SHUFFLE("Shuffle", new ShuffleEffect(), "ShuffleDesc"),
    BEARD_CAT("BeardCat", null, "BeardCatDesc", true),
    TACO_CAT("TacoCat", null, "TacoCatDesc", true),
    HAIRY_POTATO_CAT("HairyPotatoCat", null, "HairyPotatoDesc", true),
    RAINBOW_RALPHING_CAT("RainbowRalphingCat", null, "RainbowDesc", true),
    CATTERMELON("Cattermelon", null, "CattermelonDesc", true),
    FERAL_CAT("FeralCat", null, "FeralCatDesc", true),
    DRAW_FROM_THE_BOTTOM("DrawFromTheBottom", new DrawFromBottomEffect(), "DrawFromBottomDesc"),
    NOPE("Nope", null, "NopeDesc"),
    ALTER_THE_FUTURE("AlterTheFuture", new AlterTheFutureEffect(), "AlterDesc"),
    TARGETED_ATTACK("TargetedAttack", new TargetedAttackEffect(), "TargetedAttackDesc"),
    SEE_THE_FUTURE("SeeTheFuture", new SeeTheFutureEffect(), "SeeTheFutureDesc");

    public static final List<String> ENUM_VALUES = Utils.enumValuesToStrings(CardType.class.getEnumConstants());

    private final String displayName;
    private final EffectPattern effectPattern;
    private final String description;
    private final boolean isCatCard;

    CardType(String displayNameI18nKey, EffectPattern pattern, String descriptionI18nKey, boolean inIsCatCard) {
        this.displayName = I18n.getMessage(displayNameI18nKey);
        this.effectPattern = pattern;
        this.description = I18n.getMessage(descriptionI18nKey);
        this.isCatCard = inIsCatCard;
    }

    CardType(String displayNameI18nKey, EffectPattern pattern, String descriptionI18nKey) {
        this(displayNameI18nKey, pattern, descriptionI18nKey, false);
    }

    public EffectPattern getEffectPattern() {
        return this.effectPattern;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsCatCard() {
        return isCatCard;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}

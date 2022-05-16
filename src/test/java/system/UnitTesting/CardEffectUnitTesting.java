package system.UnitTesting;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.cardEffects.*;
import system.*;

public class CardEffectUnitTesting {
    @Test
    public void testDefuseBombEffectUse() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        EasyMock.replay(gameState);

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

        EasyMock.verify(gameState);
    }

    @Test
    public void testDrawFromBottom() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.drawFromBottom();
        EasyMock.expectLastCall();
        EasyMock.replay(gameState);

        drawFromBottomEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testSkip() {
        EffectPattern skipEffect = new SkipEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.transitionToNextTurn();
        EasyMock.expectLastCall();
        EasyMock.replay(gameState);

        skipEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testShuffleDeck() {
        EffectPattern shuffleEffect = new ShuffleEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.shuffleDeck();
        EasyMock.expectLastCall();
        EasyMock.replay(gameState);

        shuffleEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testSeeTheFuture() {
        EffectPattern futureEffect = new SeeTheFutureEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.seeTheFuture();
        EasyMock.replay(gameState);

        futureEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testAttack() {
        EffectPattern attackEffect = new AttackEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.transitionToNextTurn();
        gameState.addExtraTurn();
        EasyMock.replay(gameState);

        attackEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testAlterTheFuture() {
        EffectPattern alterEffect = new AlterTheFutureEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.alterTheFuture();
        EasyMock.replay(gameState);

        alterEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testTargetedAttack() {
        EffectPattern targetedAtkEffect = new TargetedAttackEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.triggerDisplayOfTargetedAttackPrompt();
        EasyMock.replay(gameState);

        targetedAtkEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }

    @Test
    public void testFavor() {
        EffectPattern favorEffect = new FavorEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        gameState.triggerDisplayOfFavorPrompt();
        EasyMock.replay(gameState);

        favorEffect.useEffect(gameState);

        EasyMock.verify(gameState);
    }
}

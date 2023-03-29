package system.UnitTesting;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.cardEffects.*;
import system.*;

import java.util.LinkedList;
import java.util.List;

class CardEffectUnitTesting {
    @Test
    void testDefuseBombEffectUse() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.transitionToNextTurn();
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        bombEffectPattern.setCurrentState(gameManager);
        Assertions.assertDoesNotThrow(bombEffectPattern::useEffect);

        EasyMock.verify(gameState, gameState, user, drawDeck);
    }

    @Test
    void testDrawFromBottom() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.transitionToNextTurn();
        EasyMock.expectLastCall();
        EasyMock.expect(drawDeck.drawFromBottomForUser(user)).andReturn(false);
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        drawFromBottomEffect.setCurrentState(gameManager);
        drawFromBottomEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

    @Test
    void testSkip() {
        EffectPattern skipEffect = new SkipEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.transitionToNextTurn();
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        skipEffect.setCurrentState(gameManager);
        skipEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

    @Test
    void testShuffleDeck() {
        EffectPattern shuffleEffect = new ShuffleEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.shuffleDeck(true);
        EasyMock.expectLastCall();
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(drawDeck.shuffle()).andReturn(true);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        shuffleEffect.setCurrentState(gameManager);
        shuffleEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

    @Test
    @SuppressWarnings("unchecked") // We know the cards mock is an unsafe type conversion
    void testSeeTheFuture() {
        EffectPattern futureEffect = new SeeTheFutureEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        List<Card> cards = EasyMock.createMock(LinkedList.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.seeTheFuture(cards);
        EasyMock.expectLastCall();
        EasyMock.expect(drawDeck.drawThreeCardsFromTop()).andReturn(cards);
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck, cards);

        futureEffect.setCurrentState(gameManager);
        futureEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck, cards);
    }

    @Test
    void testAttack() {
        EffectPattern attackEffect = new AttackEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.transitionToNextTurn();
        EasyMock.expectLastCall();
        gameState.addExtraTurn();
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        attackEffect.setCurrentState(gameManager);
        attackEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

    @Test
    @SuppressWarnings("unchecked") // We know the cards mock is an unsafe type conversion
    void testAlterTheFuture() {
        EffectPattern alterEffect = new AlterTheFutureEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        List<Card> cards = EasyMock.createMock(LinkedList.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.alterTheFuture(cards);
        EasyMock.expectLastCall();
        EasyMock.expect(drawDeck.drawThreeCardsFromTop()).andReturn(cards);
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck, cards);

        alterEffect.setCurrentState(gameManager);
        alterEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck, cards);
    }

    @Test
    @SuppressWarnings("unchecked") // We know the cards mock is an unsafe type conversion
    void testTargetedAttack() {
        EffectPattern targetedAtkEffect = new TargetedAttackEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        List<User> users = EasyMock.createMock(LinkedList.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.triggerDisplayOfTargetedAttackPrompt(users);
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getTargetsForCardEffects()).andReturn(users);
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck, users);

        targetedAtkEffect.setCurrentState(gameManager);
        targetedAtkEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck, users);
    }

    @Test
    @SuppressWarnings("unchecked") // We know the cards mock is an unsafe type conversion
    void testFavor() {
        EffectPattern favorEffect = new FavorEffect();
        GameState gameState = EasyMock.createMock(GameState.class);
        User user = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        List<User> users = EasyMock.createMock(LinkedList.class);
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.triggerDisplayOfFavorPrompt(users);
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getTargetsForCardEffects()).andReturn(users);
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck, users);

        favorEffect.setCurrentState(gameManager);
        favorEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck, users);
    }
}

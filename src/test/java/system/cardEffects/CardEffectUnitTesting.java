package system.cardEffects;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.cardEffects.*;
import system.*;

import java.util.LinkedList;
import java.util.List;

class CardEffectUnitTesting {

    private GameState gameState;
    private User user;
    private DrawDeck drawDeck;
    private GameManager gameManager;
    private List<User> users;
    private List<Card> cards;

    @BeforeEach
    void setUp() {
        gameState = EasyMock.createMock(GameState.class);
        user = EasyMock.createMock(User.class);
        drawDeck = EasyMock.createMock(DrawDeck.class);
        gameManager = EasyMock.createMock(GameManager.class);
        users = EasyMock.createMock(LinkedList.class);
        cards = EasyMock.createMock(LinkedList.class);
    }

    @Test
    void testDefuseBombEffectUse() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
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
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.transitionToNextTurn();
        EasyMock.expectLastCall();
        EasyMock.expect(drawDeck.drawFromBottomForUser(user)).andReturn(false);
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(user.getName()).andReturn("");
        EasyMock.expect(user.getLastCardInHand()).andReturn(EasyMock.createNiceMock(Card.class));
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        gameManager.postMessage(EasyMock.anyObject());
        EasyMock.expectLastCall();
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        drawFromBottomEffect.setCurrentState(gameManager);
        drawFromBottomEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

    @Test
    void testSkip() {
        EffectPattern skipEffect = new SkipEffect();
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.executeSkip();
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
    @SuppressWarnings("unchecked")
        // We know the cards mock is an unsafe type conversion
    void testSeeTheFuture() {
        EffectPattern futureEffect = new SeeTheFutureEffect();
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.executeSeeTheFuture(cards);
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
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.executeAttack();
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        attackEffect.setCurrentState(gameManager);
        attackEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

    @Test
    @SuppressWarnings("unchecked")
        // We know the cards mock is an unsafe type conversion
    void testAlterTheFuture() {
        EffectPattern alterEffect = new AlterTheFutureEffect();
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.executeAlterTheFuture(cards);
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
    @SuppressWarnings("unchecked")
        // We know the cards mock is an unsafe type conversion
    void testTargetedAttack() {
        TargetedAttackEffect targetedAtkEffect = new TargetedAttackEffect();
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        gameManager.displayTargetSelectionPrompt(CardType.TARGETED_ATTACK, targetedAtkEffect.apply);
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        EasyMock.replay(gameManager, gameState, user, drawDeck, users);

        targetedAtkEffect.setCurrentState(gameManager);
        targetedAtkEffect.useEffect();

        EasyMock.verify(gameManager, gameState, user, drawDeck, users);
    }

    @Test
    @SuppressWarnings("unchecked")
        // We know the cards mock is an unsafe type conversion
    void testFavor() {
        EffectPattern favorEffect = new FavorEffect();
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

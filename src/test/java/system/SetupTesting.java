package system;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import presentation.Gameboard;
import system.cards.DefuseCard;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.easymock.EasyMock.isA;

public class SetupTesting {
    private static final int PARTY_PACK_SIZE = 101;
    private static final int PARTY_PACK_PAW_ONLY_SIZE = 41;
    @Test
    public void testCreateUsers_fromEmptyList() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsers_fromNull() {
        Setup setup = new Setup(2);
        Executable executable = () -> setup.createUsers(null);
        Assertions.assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void testCreateUsers_fromListOfSize1() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        names.add("name");
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsers_fromListOfSize2() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 2);
    }

    @Test
    public void testCreateUsers_fromListOfSize10() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 10);
    }

    @Test
    public void testCreateUsers_fromListOfSize11() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            names.add("name" + i);
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsers_fromListWithDuplicates() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("sameName");
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeck_fromEmptyFile() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/empty.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeck_fromFileWithOneLine() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/oneline.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd2Players() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);

        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numberOfDefuseCardsToAdd = 1;
        Assertions.assertEquals(PARTY_PACK_PAW_ONLY_SIZE + numberOfDefuseCardsToAdd,
                drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numberOfDefuseCardsToAdd);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd3Players() {
        Setup setup = new Setup(3);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numberOfDefuseCardsToAdd = 0;
        Assertions.assertEquals(PARTY_PACK_PAW_ONLY_SIZE + numberOfDefuseCardsToAdd,
                drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numberOfDefuseCardsToAdd);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd4Players() {
        Setup setup = new Setup(4);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numberOfDefuseCardsToAdd = 3;
        int expectedDeckSize = PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE + numberOfDefuseCardsToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numberOfDefuseCardsToAdd);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd7Players() {
        Setup setup = new Setup(7);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numberOfDefuseCardsToAdd = 0;
        int expectedDeckSize = PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE + numberOfDefuseCardsToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numberOfDefuseCardsToAdd);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd8Players() {
        Setup setup = new Setup(8);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        Assertions.assertTrue(drawDeck.getDeckSize() == PARTY_PACK_SIZE);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd10Players() {
        Setup setup = new Setup(10);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        Assertions.assertTrue(drawDeck.getDeckSize() == PARTY_PACK_SIZE);
    }

    @Test
    public void testCreateDrawDeck_fromOneLineOfInvalidData() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/oneline_invalid.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileWithOneLineOfInvalidData() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/fullfile_invalid.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDiscardDeck(){
        Setup setup = new Setup(2);
        DiscardDeck discDeck = setup.createDiscardDeck();
        Assertions.assertTrue(discDeck.getDeckSize() == 0);
    }

    @Test
    public void testDistributeCards1User() {
        Queue<User> users = new LinkedList<>();
        users.add(new User());
        DrawDeck drawDeck = new DrawDeck();

        Setup setup = new Setup(1);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testDistributeCards2Users() {
        User player1 = EasyMock.createMock(User.class);
        User player2 = EasyMock.createMock(User.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        for (int i=0; i<7; i++) {
            drawDeck.drawInitialCard(player1);
            drawDeck.drawInitialCard(player2);
        }
        player1.addCard(isA(DefuseCard.class));
        player2.addCard(isA(DefuseCard.class));
        EasyMock.replay(player1, player2, drawDeck);

        Queue<User> users = new LinkedList<>();
        users.add(player1);
        users.add(player2);

        Setup setup = new Setup(2);
        setup.dealHands(users, drawDeck);

        EasyMock.verify(player1, player2, drawDeck);
    }

    @Test
    public void testDistributeCards10Users() {
        Queue<User> users = new LinkedList<>();
        for (int i=0; i<10; i++) {
            users.add(EasyMock.createMock(User.class));
        }
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        for (User user : users) {
            for (int i=0; i<7; i++) {
                drawDeck.drawInitialCard(user);
            }
            user.addCard(isA(DefuseCard.class));
        }

        for (User user : users) {
            EasyMock.replay(user);
        }
        EasyMock.replay(drawDeck);

        Setup setup = new Setup(10);
        setup.dealHands(users, drawDeck);

        for (User user : users) {
            EasyMock.verify(user);
        }
        EasyMock.verify(drawDeck);
    }

    @Test
    public void testDistributeCards11Users() {
        Queue<User> users = new LinkedList<>();
        for (int i=0; i<11; i++) {
            users.add(new User());
        }
        DrawDeck drawDeck = new DrawDeck();

        Setup setup = new Setup(11);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

}

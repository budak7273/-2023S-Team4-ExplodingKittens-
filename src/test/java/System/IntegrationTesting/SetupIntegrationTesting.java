package system.IntegrationTesting;

import system.Setup;
import system.User;
import system.DrawDeck;
import system.DiscardDeck;



import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SetupIntegrationTesting {
    private static final int FULL_SIZE = 101;
    private static final int PAW_ONLY_SIZE = 41;

    private static final int MAX_PLAYER_COUNT = 10;

    private static final int MAX_PAW_ONLY_COUNT = 3;
    private static final int MIN_NO_PAW_ONLY_COUNT = 4;
    private static final int MAX_NO_PAW_ONLY_COUNT = 7;
    private static final int MIN_ALL_COUNT = 8;

    private static final int INITIAL_HAND_SIZE = 7;

    @Test
    public void testCreateUsersFromEmptyList() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromNull() {
        Setup setup = new Setup(2);
        Executable executable = () -> setup.createUsers(null);
        Assertions.assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void testCreateUsersFromListOfSize1() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        names.add("name");
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromListOfSize2() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == 2);
    }

    @Test
    public void testCreateUsersFromListOfSize10() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYER_COUNT; i++) {
            names.add("name" + i);
        }
        Queue<User> queue = setup.createUsers(names);
        Assertions.assertTrue(queue.size() == MAX_PLAYER_COUNT);
    }

    @Test
    public void testCreateUsersFromListOfSize11() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYER_COUNT + 1; i++) {
            names.add("name" + i);
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateUsersFromListWithDuplicates() {
        Setup setup = new Setup(2);
        List<String> names = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            names.add("sameName");
        }
        Executable executable = () -> setup.createUsers(names);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromEmptyFile() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/empty.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFileWithOneLine() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/oneline.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd2Players() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);

        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 1;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd3Players() {
        Setup setup = new Setup(MAX_PAW_ONLY_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        Assertions.assertEquals(PAW_ONLY_SIZE + numOfDefusesToAdd,
                drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd4Players() {
        Setup setup = new Setup(MIN_NO_PAW_ONLY_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = MAX_NO_PAW_ONLY_COUNT - MIN_NO_PAW_ONLY_COUNT;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd7Players() {
        Setup setup = new Setup(MAX_NO_PAW_ONLY_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedSize = FULL_SIZE - PAW_ONLY_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedSize, drawDeck.getDeckSize());
        Assertions.assertTrue(drawDeck.getDefuseCount() == numOfDefusesToAdd);
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd8Players() {
        Setup setup = new Setup(MIN_ALL_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 2;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromFullFileAnd10Players() {
        Setup setup = new Setup(MAX_PLAYER_COUNT);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);

        int numOfDefusesToAdd = 0;
        int expectedDeckSize = FULL_SIZE + numOfDefusesToAdd;
        Assertions.assertEquals(expectedDeckSize, drawDeck.getDeckSize());
        Assertions.assertEquals(numOfDefusesToAdd, drawDeck.getDefuseCount());
    }

    @Test
    public void testCreateDrawDeckFromOneLineOfInvalidData() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/oneline_invalid.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDrawDeckFromFullFileWithOneLineOfInvalidData() {
        Setup setup = new Setup(2);
        String path = "src/test/resources/fullfile_invalid.csv";
        File cardInfoFile = new File(path);
        Executable executable = () -> setup.createDrawDeck(cardInfoFile);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testCreateDiscardDeck() {
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

//    @Test
//    public void testDistributeCards2Users() {
//        User player1 = EasyMock.createMock(User.class);
//        User player2 = EasyMock.createMock(User.class);
//        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
//        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
//            drawDeck.drawInitialCard(player1);
//            drawDeck.drawInitialCard(player2);
//        }
//        player1.addCard(eq(new Card(CardType.DEFUSE)));
//        player2.addCard(eq(new Card(CardType.DEFUSE)));
//        EasyMock.replay(player1, player2, drawDeck);
//
//        Queue<User> users = new LinkedList<>();
//        users.add(player1);
//        users.add(player2);
//
//        Setup setup = new Setup(2);
//        setup.dealHands(users, drawDeck);
//
//        EasyMock.verify(player1, player2, drawDeck);
//    }
//
//    @Test
//    public void testDistributeCards10Users() {
//        Queue<User> users = new LinkedList<>();
//        for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
//            users.add(EasyMock.createMock(User.class));
//        }
//        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
//
//        for (User user : users) {
//            for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
//                drawDeck.drawInitialCard(user);
//            }
//            user.addCard(eq(new Card(CardType.DEFUSE)));
//        }
//
//        for (User user : users) {
//            EasyMock.replay(user);
//        }
//        EasyMock.replay(drawDeck);
//
//        Setup setup = new Setup(MAX_PLAYER_COUNT);
//        setup.dealHands(users, drawDeck);
//
//        for (User user : users) {
//            EasyMock.verify(user);
//        }
//        EasyMock.verify(drawDeck);
//    }

    @Test
    public void testDistributeCards11Users() {
        Queue<User> users = new LinkedList<>();
        for (int i = 0; i < MAX_PLAYER_COUNT + 1; i++) {
            users.add(new User());
        }
        DrawDeck drawDeck = new DrawDeck();

        Setup setup = new Setup(MAX_PLAYER_COUNT + 1);
        Executable executable = () -> setup.dealHands(users, drawDeck);
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

}

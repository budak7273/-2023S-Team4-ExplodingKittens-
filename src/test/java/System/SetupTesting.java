package system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SetupTesting {
    private static final int PARTY_PACK_SIZE = 120;
    private static final int PARTY_PACK_PAW_ONLY_SIZE = 44;
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
        Assertions.assertTrue(drawDeck.getDeckSize() == 44);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd3Players() {
        Setup setup = new Setup(3);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        Assertions.assertTrue(drawDeck.getDeckSize() == 44);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd4Players() {
        Setup setup = new Setup(4);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        Assertions.assertTrue(drawDeck.getDeckSize() == PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE);
    }

    @Test
    public void testCreateDrawDeck_fromFullFileAnd7Players() {
        Setup setup = new Setup(7);
        String path = "src/test/resources/fullfile.csv";
        File cardInfoFile = new File(path);
        DrawDeck drawDeck = setup.createDrawDeck(cardInfoFile);
        Assertions.assertTrue(drawDeck.getDeckSize() == PARTY_PACK_SIZE - PARTY_PACK_PAW_ONLY_SIZE);
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

}

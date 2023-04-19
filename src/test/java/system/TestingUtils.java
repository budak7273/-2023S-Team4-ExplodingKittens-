package system;

import org.easymock.EasyMock;

import javax.swing.JFrame;
import java.awt.Container;
import java.util.Random;

public class TestingUtils {

    private TestingUtils() {
        throw new IllegalStateException("Do not instantiate a utility class");
    }
    public static final int TESTS_RANDOM_SEED = 0;

    public static Random getTestRandom() {
        return new Random(TESTS_RANDOM_SEED);
    }

    public static JFrame getFakeFrame() {
        JFrame mock = EasyMock.createNiceMock(JFrame.class);
        Container mockedContainer = EasyMock.createNiceMock(Container.class);
        EasyMock.expect(mock.getContentPane()).andReturn(mockedContainer).anyTimes();
        EasyMock.replay(mock, mockedContainer);
        return mock;
    }
}

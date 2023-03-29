package system;

import java.util.Random;

public class TestingUtils {

    private TestingUtils() {
        throw new IllegalStateException("Do not instantiate a utility class");
    }
    public static final int TESTS_RANDOM_SEED = 0;

    public static Random getTestRandom() {
        return new Random(TESTS_RANDOM_SEED);
    }
}

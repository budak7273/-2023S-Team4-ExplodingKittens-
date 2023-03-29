package system;

import java.util.Random;

public class TestingUtils {
    public static final int TESTS_RANDOM_SEED = 0;

    public static Random getTestRandom() {
        return new Random(TESTS_RANDOM_SEED);
    }
}

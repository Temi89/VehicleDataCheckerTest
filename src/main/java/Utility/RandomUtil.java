package Utility;

import java.util.Random;

public class RandomUtil {

    private static final int MIN_MILEAGE = 1000;
    private static final int MAX_MILEAGE = 100000;

    // Generates a random mileage value within the specified range
    public static int generateRandomMileage() {
        Random random = new Random();
        return random.nextInt((MAX_MILEAGE - MIN_MILEAGE) + 1) + MIN_MILEAGE;
    }
}
package utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {
    public static String getRandomValue(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }
}

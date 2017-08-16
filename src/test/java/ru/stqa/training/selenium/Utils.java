package ru.stqa.training.selenium;

import org.apache.commons.lang3.RandomStringUtils;

class Utils {
    static String getRandomValue(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length).toLowerCase();
    }
}

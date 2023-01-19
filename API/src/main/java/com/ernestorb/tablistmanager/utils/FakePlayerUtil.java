package com.ernestorb.tablistmanager.utils;

import java.util.Random;

public class FakePlayerUtil {

    private FakePlayerUtil() {
    }

    /**
     * @return String that always start with "zz" + 8 [A-Za-z0] characters
     */
    public static String randomName() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();
        String generatedString = "zz";
        generatedString += random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

}

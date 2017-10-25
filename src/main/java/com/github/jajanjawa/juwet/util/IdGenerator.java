package com.github.jajanjawa.juwet.util;

public class IdGenerator {
    private static RandomString randomString;
    public static final int ID_LENGTH = 10;

    static {
        randomString = new RandomString();
        randomString.alphanumeric();
    }

    public static String generate() {
        return randomString.generate(ID_LENGTH);
    }
}

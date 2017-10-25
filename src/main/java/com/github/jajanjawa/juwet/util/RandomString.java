package com.github.jajanjawa.juwet.util;

import java.util.Random;

public class RandomString {
    private static Random random = new Random();
    private static final String NUMERIC = "0123456789";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private StringBuilder dictionary;

    public RandomString() {
        dictionary = new StringBuilder();
    }

    public RandomString numeric() {
        dictionary.append(NUMERIC);
        return this;
    }

    public RandomString alphaUppercase() {
        dictionary.append(UPPERCASE);
        return this;
    }

    public RandomString alphaLowercase() {
        dictionary.append(LOWERCASE);
        return this;
    }

    public RandomString alphanumeric() {
        numeric().alphaUppercase().alphaLowercase();
        return this;
    }

    public void clear() {
        dictionary.setLength(0);
    }

    public String generate(int length) {
        int size = dictionary.length();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = dictionary.charAt(random.nextInt(size));
            builder.append(c);
        }
        return builder.toString();
    }
}

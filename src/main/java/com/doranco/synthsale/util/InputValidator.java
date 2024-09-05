package com.doranco.synthsale.util;

import java.util.regex.Pattern;

public class InputValidator {

    // Pattern pour interdire les caractères dangereux
    private static final String INVALID_INPUT_PATTERN = "^[^<>\\{\\}\\[\\]\"'%]+$";

    public static boolean isValidInput(String input) {
        if (input == null) {
            return false;
        }
        return Pattern.matches(INVALID_INPUT_PATTERN, input);
    }
}

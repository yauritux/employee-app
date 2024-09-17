package com.cercli.shared.util;

import java.util.regex.Pattern;

/**
 * @author Yauri Attamimi (yauritux@gmail.com)
 * @version 1.0
 */
public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

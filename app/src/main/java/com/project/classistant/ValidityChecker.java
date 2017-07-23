package com.project.classistant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the class to check the validity of Email and Password!
 */

public class ValidityChecker {
    private static final String EMAIL_PATTERN="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN="((?=.*\\d)(?=.*[a-z])(?=.*[@#$%_!?]).{6,20})";
    static private Pattern pattern;
    static private Matcher matcher;

    /**
     * NOTE: This is the method to check the validity of the email ID.
     * @param email: The email which is entered by the user.
     * @validity: True if the email is valid else false.
     */
    public static boolean checkValidityEmail(String email){
        pattern=Pattern.compile(EMAIL_PATTERN);
        matcher=pattern.matcher(email);
        return matcher.matches();
    }

    /**
     *  must contains one digit from 0-9
     *  must contains one lowercase characters
     *	must contains one special symbols in the list "@#$%_!?"
     *  length at least 6 characters and maximum of 20
     * @param password: The password user entered.
     * @return: true if the password is valid. Else False.
     */
    public static boolean checkValidityPassword(String password){
        pattern=Pattern.compile(PASSWORD_PATTERN);
        matcher=pattern.matcher(password);
        return matcher.matches();
    }
}

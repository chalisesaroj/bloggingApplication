package com.udemy.learn.blogging.payload;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
@Service
public class StrongPasswordChecker {
    public  boolean isStrongPassword(String password) {
        // Check for minimum length (e.g., 8 characters)
        if (password.length() < 8) {
            return false;
        }

        // Check for both uppercase and lowercase letters
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            }
        }
        if (!hasUppercase || !hasLowercase) {
            return false;
        }

        // Check for at least one digit
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            return false;
        }

        // Check for at least one special character
        Pattern specialCharsPattern = Pattern.compile("[!@#$%^&*]");
        Matcher matcher = specialCharsPattern.matcher(password);
        if (!matcher.find()) {
            return false;
        }

        // If all checks pass, it's a strong password
        return true;
    }

  
}

package com.crni99.qrcodegenerator.config;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

@Component
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
    private Set<String> generatedPasswords = new HashSet<>();

    public String generatePassword(int length) {
        SecureRandom random = new SecureRandom();
        String password;

        do {
            StringBuilder passwordBuilder = new StringBuilder();

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(CHARACTERS.length());
                passwordBuilder.append(CHARACTERS.charAt(index));
            }

            password = passwordBuilder.toString();
        } while (!generatedPasswords.add(password));

        return password;
    }
}


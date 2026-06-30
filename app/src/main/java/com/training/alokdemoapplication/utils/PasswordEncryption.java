package com.training.alokdemoapplication.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordEncryption {
    private static final int SALT_LENGTH = 16;

    /**
     * Encrypts a password using SHA-256 with salt
     */
    public static String hashPassword(String password) {
        try {
            // Generate random salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Hash password with salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Combine salt and hash
            byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);

            // Encode to Base64 for storage
            return Base64.getEncoder().encodeToString(saltAndHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Verifies a password against its hash
     */
    public static boolean verifyPassword(String password, String hash) {
        try {
            // Decode the hash
            byte[] saltAndHash = Base64.getDecoder().decode(hash);

            // Extract salt and hash
            byte[] salt = new byte[SALT_LENGTH];
            byte[] storedHash = new byte[saltAndHash.length - SALT_LENGTH];
            System.arraycopy(saltAndHash, 0, salt, 0, SALT_LENGTH);
            System.arraycopy(saltAndHash, SALT_LENGTH, storedHash, 0, storedHash.length);

            // Hash the provided password with the same salt
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Compare hashes
            return MessageDigest.isEqual(hashedPassword, storedHash);
        } catch (Exception e) {
            return false;
        }
    }
}

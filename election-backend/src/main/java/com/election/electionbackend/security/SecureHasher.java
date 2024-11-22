package com.election.electionbackend.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;


public class SecureHasher {
    // Configure a password hasher
    private final SecureRandom random;
    private static byte[] salt;

    public SecureHasher() {
        random = new SecureRandom();
        salt = new byte[16];
        random.nextBytes(salt);
    }

    private static MessageDigest md = getMessageDigest("SHA-512");

    private static MessageDigest getMessageDigest(String algorithm) {
        //System.out.println("GetMD-" + algorithm);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
            // apply a secret salt to the hasher
            md.update(salt);
            md.reset();
            return md;
        } catch (Exception ex) {
            // try to fallback on SHA-256
            if (!algorithm.equals("SHA-256"))
                return getMessageDigest("SHA-256");
        }
        // nothing found
        return null;
    }

    /**
     * Calculate a secure hash from a soource for the purpose of password hashing.
     *
     * @param source
     * @return
     */
    public static String secureHash(String source, long delay) throws InterruptedException {
        if (md == null || source == null) return null;
        // hash the source using the salted SHA hasher
        byte[] hashedResult = md.digest(source.getBytes(StandardCharsets.UTF_8));
        Thread.sleep(delay);
        // convert the hashed result to a string
        String result = Base64.getEncoder().encodeToString(hashedResult);
        //System.out.println(source + " was hashed into " + result);
        return result;
    }
}

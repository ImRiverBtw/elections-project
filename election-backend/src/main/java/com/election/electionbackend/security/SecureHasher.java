package com.election.electionbackend.security;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;


public class SecureHasher {
    // Configure a password hasher
    private static final SecureRandom random = new SecureRandom();
    private static final byte[] salt = new byte[16];

    static {
        random.nextBytes(salt);
    }

    private static final MessageDigest md = getMessageDigest("SHA-512");

    private static MessageDigest getMessageDigest(String algorithm) {
        //System.out.println("GetMD-" + algorithm);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
            // apply a secret salt to the hasher
            md.update(salt);
            return md;
        } catch (Exception ex) {
            // try to fallback on SHA-256
            if (!algorithm.equals("SHA-256"))
                return getMessageDigest("SHA-256");
        }
        // nothing found
        throw new IllegalStateException("Unable to initialize MessageDigest with any algorithm");
    }

    /**
     * Calculate a secure hash from a source for the purpose of password hashing.
     *
     * @param source
     * @return the hashed source
     */
    public static String secureHash(String source) {
        // hash the source using the salted SHA hasher
        byte[] hashedResult = md.digest(source.getBytes(StandardCharsets.UTF_8));
        // convert the hashed result to a string
        return Base64.getEncoder().encodeToString(hashedResult);
    }
}

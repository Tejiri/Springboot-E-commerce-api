package com.example.store.utils;


import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Component
public class StringHasher {
    public String hashString(String stringToHash) {
        try {

            byte[] salt = {
                    Byte.parseByte("-86"),
                    Byte.parseByte("84"),
                    Byte.parseByte("67"),
                    Byte.parseByte("-40"),
                    Byte.parseByte("-27"),
                    Byte.parseByte("-73"),
                    Byte.parseByte("-77"),
                    Byte.parseByte("73"),
                    Byte.parseByte("-104"),
                    Byte.parseByte("-43"),
                    Byte.parseByte("31"),
                    Byte.parseByte("-51"),
                    Byte.parseByte("83"),
                    Byte.parseByte("-44"),
                    Byte.parseByte("32"),
                    Byte.parseByte("118")
            };

            KeySpec spec = new PBEKeySpec(stringToHash.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            return String.format("%x", new BigInteger(hash));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("String hashing failed");
        }

    }
}
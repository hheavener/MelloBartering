package com.mello.mello.Util;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class Hash {

    public static String SHA_256(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return DatatypeConverter.printHexBinary(
                MessageDigest.getInstance("SHA-256").digest(text.getBytes("UTF-8")));
    }

    public static String getNewSalt() {
        Random r = new SecureRandom();
        byte[] saltBytes = new byte[32];
        r.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static String hashAndSalt(String password, String salt)
            throws NoSuchAlgorithmException {
        return hashString(password + salt);
    }

    public static String hashString(String password)
            throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for (byte b : mdArray) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

}

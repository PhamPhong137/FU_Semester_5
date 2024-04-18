/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author 
 */
public class SHAHash {

    private SHAHash() {
        throw new IllegalStateException("Utility class");
    }

    public static String createSHA(String s) throws NoSuchAlgorithmException {
        // Static getInstance() method is invoked with the hashing SHA-256  
        MessageDigest msgDgst = MessageDigest.getInstance("SHA-256");

        // the digest() method is invoked  
        // to compute the message digest of the input  
        // and returns an array of byte  
        byte[] hash = msgDgst.digest(s.getBytes(StandardCharsets.UTF_8));

        // Converting the byte array in the signum representation  
        BigInteger no = new BigInteger(1, hash);

        // Converting the message digest into the hex value  
        StringBuilder hexStr = new StringBuilder(no.toString(16));

        // Padding with tbe leading zeros  
        while (hexStr.length() < 32) {
            hexStr.insert(0, '0');
        }

        return hexStr.toString();
    }
}

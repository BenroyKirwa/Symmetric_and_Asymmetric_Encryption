package com.asymmetricencryption;

import javax.crypto.Cipher;
import javax.crypto.CipherSpi;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Scanner;

public class AsymmetricEncryption {

    public static KeyPair generateRsaKeyPair() throws Exception{

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // Define the size of the key
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encrypt(String message, PublicKey publicKey) throws Exception{

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public static String decryption(byte [] cipherText, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte [] decryptedOriginalArray = cipher.doFinal(cipherText);
        return new String(decryptedOriginalArray);
    }

    public static void main(String[] args) throws Exception {

        // Ask user for input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the message to encrypt : ");
        String userMessage = input.nextLine();

        // Step 2  : Generate a rsa key pair
        KeyPair keyPair = generateRsaKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Step 3 : Implement Encryption
        byte[] cipherText = encrypt(userMessage, publicKey);

        // Step 4 : Print the cipher text
        System.out.println("The original text is : " + userMessage);
        System.out.println("Cipher text is : " + new String(cipherText));

        // Step 5 : Decryption
        String originalMessage = decryption(cipherText, privateKey);
        System.out.println("The decrypted text from cipher is : " + originalMessage);
    }
}

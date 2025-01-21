package com.tamperingdetection;

import java.io.FileOutputStream;
import java.security.*;
import java.util.Base64;

public class TamperingDetection {

    public static KeyPair generateRsaKeyPair() throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        // Define the size of the key
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    public static void savePrivateKeyToFile(PrivateKey privateKey, String filepath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filepath);
        fos.write(privateKey.getEncoded());
        fos.close();
    }

    public static void savePublicKeyToFile(PublicKey publicKey, String filepath) throws Exception {
        FileOutputStream fos = new FileOutputStream(filepath);
        fos.write(publicKey.getEncoded());
        fos.close();
    }

    private static byte[] signString(byte[] string, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(string);
        return signature.sign();
    }

    public static boolean verifySignature(byte[] string, byte[] digitalSignature, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(string);
        return signature.verify(digitalSignature);

    }

    public static void main(String[] args) throws Exception {

        // Step 1 : Generate the RSA key pair

        KeyPair keyPair = generateRsaKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Step 2 : Save the private and public key to a file

        savePrivateKeyToFile(privateKey, "private_key.pem");
        savePublicKeyToFile(publicKey, "public_key.pem");
        // Step 3 : Read file to be signed
        String string = "Hello World!";
        // Step 4 : Sign the document using private key and generate a digital signature

        byte[] digitalSignature = signString(string.getBytes(), privateKey);

        // Step 5 Tamper the message

        string = "Hello SkyWorld!";

        // Step 6 : Saves the digital signature

        System.out.println("The digital signature is : " + Base64.getEncoder().encodeToString(digitalSignature));

        // Step 7 : Verify the signature using the public key and prints verification result.

        boolean verified = verifySignature(string.getBytes(), digitalSignature, publicKey);
        if (verified) System.out.println("The signature is verified.");
        else System.out.println("The signature is not verified!!");

    }


}

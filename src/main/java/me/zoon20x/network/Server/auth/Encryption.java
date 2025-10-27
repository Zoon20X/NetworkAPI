package me.zoon20x.network.Server.auth;

import me.zoon20x.network.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Encryption {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public Encryption(){
        try {
            loadKeys();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public Encryption(String publicKey){
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            this.publicKey = keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadKeys() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicFile = new File("public.key");
        File privateFile = new File("private.key");
        if (!publicFile.exists() || !privateFile.exists()) {
            loadNewKeys();
            return;
        }
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        String publicKey = Files.readString(publicFile.toPath()).replace("-----BEGIN RSA PUBLIC KEY-----", "").replace("-----END RSA PUBLIC KEY-----", "");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getMimeDecoder().decode(publicKey));
        this.publicKey = keyFactory.generatePublic(publicKeySpec);

        String privateKey = Files.readString(privateFile.toPath()).replace("-----BEGIN RSA PRIVATE KEY-----", "").replace("-----END RSA PRIVATE KEY-----", "");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getMimeDecoder().decode(privateKey));
        this.privateKey = keyFactory.generatePrivate(privateKeySpec);
        Logger.info("Loaded Encryption Keys");

    }

    private void loadNewKeys() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        publicKey = pair.getPublic();
        String publicEncodeKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        String pekFile = "-----BEGIN RSA PUBLIC KEY-----\n" +
                publicEncodeKey + "\n" +
                "-----END RSA PUBLIC KEY-----";
        privateKey = pair.getPrivate();
        String privateEncodeKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        String prekFile = "-----BEGIN RSA PRIVATE KEY-----\n" +
                privateEncodeKey + "\n" +
                "-----END RSA PRIVATE KEY-----";
        try (FileOutputStream fos = new FileOutputStream("public.key")) {
            fos.write(pekFile.getBytes());
        }
        try (FileOutputStream fos = new FileOutputStream("private.key")) {
            fos.write(prekFile.getBytes());
        }
        Logger.info("Generated Encryption Keys");
    }

    public String encrypt(String value){
        Cipher encryptCipher = null;
        try {
            encryptCipher = Cipher.getInstance("RSA");
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] secretMessageBytes = value.getBytes(StandardCharsets.UTF_8);
        try {
            byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            return Base64.getEncoder().encodeToString(encryptedMessageBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String value){
        Cipher decryptCipher = null;
        try {
            decryptCipher = Cipher.getInstance("RSA");
            decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] decryptedMessageBytes = null;
        try {
            decryptedMessageBytes = decryptCipher.doFinal(Base64.getDecoder().decode(value));
            String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
            return decryptedMessage;

        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}

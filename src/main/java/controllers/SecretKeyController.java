package controllers;

import util.CipherBlockSizes;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.KeyStore.SecretKeyEntry;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

public class SecretKeyController {
    private KeyStore ks;
    private char[] pw;


    public SecretKeyController(KeyStore ks, char[] pw){
        this.ks = ks;
        this.pw = pw;
    }

    private char[] getPw(){
        return this.pw;
    }
    public void setPw(char[] pw){
        this.pw = pw;
    }

    public void wipePw(){
        Arrays.fill(this.getPw(), (char) 0);
    }

    private void storeKey(SecretKey key, String alias){
        SecretKeyEntry entry = new SecretKeyEntry(key);
        try{
            this.getKeyStore().setKeyEntry(alias, key, this.getPw(), null);
            System.out.printf("Success storing key. Alias: %s", alias);

        }catch(KeyStoreException e){
            System.out.printf("Error accessing keystore: %s", e.getMessage());
        }
    }

    public SecretKey getKey(String alias){
        SecretKey key = null;
        try{
            key = (SecretKey)this.getKeyStore().getKey(alias, this.getPw());
        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            System.out.printf("Error retrieving key: %s\n alias: %s\n", e.getMessage(), alias);
        }
        return key;

    }

    public void initCipher(String algorithm, String blockMode, String padding){
        String transformation = String.format("%s/%s/%s", algorithm, blockMode, padding);
        try{
            Cipher cipher = Cipher.getInstance(transformation, "BC");


        }catch(NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException e){
            System.out.printf("transformation not valid: %s\n error: %s", transformation, e.getMessage());
        }
    }

    //this is dependent on the block size of the chosen algorithm
    public IvParameterSpec initIv(String algorithm){
        int size = CipherBlockSizes.fromString(algorithm).getIvSize();
        byte[] iv = new byte[size];
        SecureRandom secureRand = new SecureRandom();
        secureRand.nextBytes(iv);
        return new IvParameterSpec(iv);

    }

    public void generateKey(String cipher, int keyLength, String alias){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
            keyGen.init(keyLength);
            SecretKey key = keyGen.generateKey();
            this.storeKey(key, alias);

            //troubleshooting below
            byte[] keyBytes = key.getEncoded();
            String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
            System.out.printf("Key generated successfully.\n Algorithm: %s\n key length: %s\n value: %s\n", cipher, keyLength, encodedKey);
        }catch(NoSuchAlgorithmException e){
            System.out.printf("error for %s cipher: %s", cipher, e.getMessage());
        }
    }






    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }
    public KeyStore getKeyStore(){
        return this.ks;
    }
}

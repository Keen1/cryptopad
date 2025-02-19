package controllers;

import util.enums.CipherBlockSizes;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.KeyStore.SecretKeyEntry;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Iterator;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

public class SecretKeyController {
    private KeyStore ks;
    private char[] pw;
    private static final String TEST_STORE_PATH = "test_ks.jks";


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

    public void storeKey(SecretKey key, String alias) throws KeyStoreException {
        try{
            this.getKeyStore().setKeyEntry(alias, key, this.getPw(), null);
            System.out.printf("Success storing key. Alias: %s", alias);
            this.saveStore();

        }catch(KeyStoreException e){
            System.out.printf("Error accessing keystore: %s", e.getMessage());
            throw new KeyStoreException(e);
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



    public boolean hasKeyForAlias(String alias) throws KeyStoreException {
        return this.getKeyStore().containsAlias(alias);
    }

    //this is dependent on the block size of the chosen algorithm
    public IvParameterSpec initIv(String algorithm){
        int size = CipherBlockSizes.fromString(algorithm).getIvSize();
        byte[] iv = new byte[size];
        SecureRandom secureRand = new SecureRandom();
        secureRand.nextBytes(iv);
        return new IvParameterSpec(iv);

    }

    public SecretKey generateKey(String cipher, int keyLength){
        SecretKey key = null;
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
            keyGen.init(keyLength);
            key = keyGen.generateKey();

            //troubleshooting below
            byte[] keyBytes = key.getEncoded();
            String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
            System.out.printf("Key generated successfully.\n Algorithm: %s\n key length: %s\n value: %s\n", cipher, keyLength, encodedKey);
        }catch(NoSuchAlgorithmException e){
            System.out.printf("error for %s cipher: %s", cipher, e.getMessage());
        }
        return key;
    }






    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }
    public KeyStore getKeyStore(){
        return this.ks;
    }

    public void showAliases(){
        try{
            Enumeration<String> aliases = this.getKeyStore().aliases();
            while(aliases.hasMoreElements()){
                System.out.printf("Entry alias: %s\n", aliases.nextElement());
            }


        }catch(KeyStoreException e){
            System.out.println("Error printing key aliases");
        }

    }

    private void saveStore(){
        try(FileOutputStream outStream = new FileOutputStream(TEST_STORE_PATH)){
            this.getKeyStore().store(outStream, this.getPw());
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e){
            System.out.printf("Error storing file: %s\n", e.getMessage());
        }
    }
}

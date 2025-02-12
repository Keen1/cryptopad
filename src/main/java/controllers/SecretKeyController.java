package controllers;

import javax.crypto.SecretKey;
import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import javax.crypto.KeyGenerator;

public class SecretKeyController {
    private KeyStore ks;
    private char[] pw;


    public SecretKeyController(KeyStore ks){
        this.ks = ks;
    }

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



    public SecretKey generateKey(String algorithm, int length){
        SecretKey key = null;
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(length);
            key = keyGen.generateKey();
        }catch(NoSuchAlgorithmException e){
            System.out.printf("No algorithm named %s, %s", algorithm, e.getMessage());
        }

        return key;
    }
    public void storeKey(SecretKey key, String alias){
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

    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }
    public KeyStore getKeyStore(){
        return this.ks;
    }
}

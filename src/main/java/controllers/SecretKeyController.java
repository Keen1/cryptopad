package controllers;

import javax.crypto.SecretKey;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
public class SecretKeyController {
    private KeyStore ks;

    public SecretKeyController(KeyStore ks){
        this.ks = ks;
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
    public void storeKey(SecretKey key){
    }

    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }
    public KeyStore getKeyStore(){
        return this.ks;
    }
}

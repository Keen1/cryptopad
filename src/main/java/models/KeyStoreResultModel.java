package models;

import java.security.KeyStore;

public class KeyStoreResultModel {
    private final KeyStore keyStore;
    private final String message;
    public KeyStoreResultModel(KeyStore keyStore, String message){
        this.keyStore = keyStore;
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public KeyStore getKeyStore(){
        return this.keyStore;
    }
}

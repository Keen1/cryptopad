package models;

import java.security.KeyStore;

public class KeyStoreResultModel {
    private final KeyStore keyStore;
    private final String message;
    private final char[] pw;

    public KeyStoreResultModel(KeyStore keyStore, String message){
        this.keyStore = keyStore;
        this.message = message;
        this.pw = null;
    }

    public KeyStoreResultModel(KeyStore keyStore, String message, char[] pw){
        this.keyStore = keyStore;
        this.message = message;
        this.pw = pw;
    }

    public char[] getPw(){
        return this.pw;
    }
    public String getMessage(){
        return this.message;
    }
    public KeyStore getKeyStore(){
        return this.keyStore;
    }

    public boolean isSuccess(){
        return this.keyStore != null;
    }
}

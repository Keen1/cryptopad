package controllers;

import models.KeyStoreModel;

import javax.crypto.SecretKey;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

public class KeyStoreController {

    private KeyStoreModel model;
    private char[] pw;

    public KeyStoreController(){
        this.model = new KeyStoreModel();
    }
    public KeyStoreController(KeyStoreModel model){
        this.model = model;
    }
    public KeyStoreController(KeyStoreModel model, char[] pw){
        this.model = model;
        this.pw = pw;
    }

    private char[] getPw(){
        return this.pw;
    }

    private void setPw(char[] pw){
        this.pw = pw;
    }

    private KeyStoreModel getModel(){
        return this.model;
    }
    public void setModel(KeyStoreModel model){
        this.model = model;
    }

    public boolean keyExists(String alias){

        try{
            if(this.getModel().hasKeyForAlias(alias)){
                return true;
            }
        }catch(KeyStoreException e){
            System.out.printf("Error accessing %s key: %s", alias, e.getMessage());
        }

        return false;
    }

    public SecretKey generateKey(String cipher, int keyLength){
        try{
            return this.getModel().generateKey(cipher, keyLength);
        }catch(NoSuchAlgorithmException e){
            System.out.printf("Error generating key for cipher: %s\n, error: %s", cipher, e.getMessage());

        }
        return null;
    }

    public void removeKey(String alias){

        try{
            this.getModel().removeKey(alias);

        }catch(KeyStoreException e){
            System.out.printf("Error deleting key: %s", e.getMessage());
        }

    }


    public SecretKey getKey(String alias){

        SecretKey key = null;

        try{

            key = this.getModel().getKey(alias, this.getPw());

        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            System.out.printf("Error retrieving key: %s", e.getMessage());
        }
        return key;
    }



}

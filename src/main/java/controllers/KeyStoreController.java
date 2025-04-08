package controllers;

import models.KeyStoreModel;

import java.security.KeyStoreException;

public class KeyStoreController {

    private KeyStoreModel model;

    public KeyStoreController(){
        this.model = new KeyStoreModel();
    }
    public KeyStoreController(KeyStoreModel model){
        this.model = model;
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

}

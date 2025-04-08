package controllers;

import components.KeyStoreSetupPanel;
import models.KeyStoreModel;
import util.KeyStoreFactory;
import util.constants.AppConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KeyStoreSetupController {
    private final KeyStoreSetupPanel setupPanel;
    private boolean requirementsMet;
    private char[] pw;
    private Runnable onSetupComplete;
    private KeyStoreModel keyStoreModel;
    public KeyStoreSetupController(KeyStoreSetupPanel setupPanel){
        this.setupPanel = setupPanel;
        this.requirementsMet = false;
        this.keyStoreModel = new KeyStoreModel();

    }

    public KeyStoreSetupController(KeyStoreSetupPanel setupPanel, Runnable onSetupComplete){
        this.setupPanel = setupPanel;
        this.onSetupComplete = onSetupComplete;
        this.requirementsMet = false;
        this.keyStoreModel = new KeyStoreModel();
    }

    public void setOnSetupComplete(Runnable onSetupComplete){
        this.onSetupComplete = onSetupComplete;
    }

    public void setPassword(char[] pw){
        this.pw = pw;
    }
    public char[] getPassword(){
        return this.pw;
    }

    public KeyStoreSetupPanel getSetupPanel() {
        return this.setupPanel;
    }

    public KeyStoreModel getKeyStoreModel(){
        return this.keyStoreModel;
    }
    public void setRequirementsMet(boolean requirementsMet){
        this.requirementsMet = requirementsMet;
    }
    public boolean areRequirementsMet(){
        return this.requirementsMet;
    }

    public void createKeyStore(){

        if(this.getPassword() != null){

            try{
                this.getKeyStoreModel().createKeyStore();
                if(onSetupComplete != null){
                    onSetupComplete.run();
                }
            }catch(Exception e){
                System.out.printf("Error accessing keystore: %s", e.getMessage());
            }





        }
    }
}

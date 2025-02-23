package controllers;

import components.KeyStoreSetupPanel;
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
    public KeyStoreSetupController(KeyStoreSetupPanel setupPanel){
        this.setupPanel = setupPanel;
        this.requirementsMet = false;

    }

    public KeyStoreSetupController(KeyStoreSetupPanel setupPanel, Runnable onSetupComplete){
        this.setupPanel = setupPanel;
        this.onSetupComplete = onSetupComplete;
        this.requirementsMet = false;
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
    public void setRequirementsMet(boolean requirementsMet){
        this.requirementsMet = requirementsMet;
    }
    public boolean areRequirementsMet(){
        return this.requirementsMet;
    }
    public void createKeyStore(){
        if(this.getPassword() != null){
            try{
                Path cryptopadDir = Paths.get(System.getProperty("user.home"), AppConstants.APP_FOLDER_NAME);
                Files.createDirectories(cryptopadDir);

                KeyStoreFactory.generateKeyStore(this.getPassword(), AppConstants.KEYSTORE_PATH);
                if(onSetupComplete != null){
                    onSetupComplete.run();
                }

            }catch(IOException e){
                System.out.printf("error creating path: %s", e.getMessage());
            }

        }
    }
}

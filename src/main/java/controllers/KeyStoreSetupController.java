package controllers;

import components.KeyStoreSetupPanel;
import crypters.KeyStoreBuilder;

public class KeyStoreSetupController {
    private final KeyStoreSetupPanel setupPanel;
    private boolean requirementsMet;
    private char[] pw;
    public KeyStoreSetupController(KeyStoreSetupPanel setupPanel){
        this.setupPanel = setupPanel;
        this.requirementsMet = false;

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
            KeyStoreBuilder builder = new KeyStoreBuilder(this.getPassword());
            try{
                builder.generateKeystore(this.getPassword());

            }catch(Exception e){
                System.out.printf("Error creating keystore: %s\n%s", e.getMessage(),e.getClass());
            }

        }
    }
}

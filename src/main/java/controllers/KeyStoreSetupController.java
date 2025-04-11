package controllers;

import components.KeyStoreSetupPanel;
import models.KeyStoreModel;

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

    //same issue here im having in other prelim panels...accessing the model directly and no set procedure on the
    //handling of the password
    //need to have a set way of setting the password, loading the keystore and retaining the password for future use
    //if necessary
    //also same problem here with accessing the model directly and not through the controller. this is a little different
    //here though because if we are instantiating this panel we know that the keystore file does not exist yet so the
    //model being instantiated here is a little less concerning to me...

    public void createKeyStore(){

        if(this.getPassword() != null){
            this.getKeyStoreModel().setPw(this.getPassword());

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

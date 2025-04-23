package handlers.keystore;

import controllers.KeyStoreSetupController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyStorePwHandler implements ActionListener {
    private KeyStoreSetupController controller;
    public KeyStorePwHandler(KeyStoreSetupController controller){
        this.controller = controller;
    }
    public KeyStoreSetupController getController(){
        return this.controller;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if(this.getController().areRequirementsMet()){
            //need to check and see if this is necessary or if this can cut any other calls out of the process when
            //retaining the password
            //this.getController().getKeyStoreModel().setPw(this.getController().getPassword());
            this.getController().setPassword(this.getController().getSetupPanel().getPasswordField().getPassword());

        }
    }
}

package util;

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
            this.getController().setPassword(this.getController().getSetupPanel().getPasswordField().getPassword());

        }
    }
}

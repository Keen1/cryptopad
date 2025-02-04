package handlers.matcher;

import controllers.KeyStoreSetupController;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;
/*
* password match handler - handles the matching of passwords on change of content in the password field and the confirm
* field of the keystore setup panel
*/

public class PasswordMatchHandler implements DocumentListener {

    //requires the controller to operate on the required fields
    private final KeyStoreSetupController controller;

    //constructor
    public PasswordMatchHandler(KeyStoreSetupController controller){
        this.controller = controller;
    }

    //getter for controller
    public KeyStoreSetupController getController(){
        return this.controller;
    }

    //override the insert, remove, and changed update methods
    @Override
    public void insertUpdate(DocumentEvent e){
        checkPasswords();
    }

    @Override
    public void removeUpdate(DocumentEvent e){
        checkPasswords();
    }

    @Override
    public void changedUpdate(DocumentEvent e){
        checkPasswords();
    }

    //check passwords function that retieves the current entries in the pw and confirm fields to ensure they match
    private void checkPasswords(){
        char[] pw = this.getController().getSetupPanel().getPasswordField().getPassword();
        char[] confirmPw = this.getController().getSetupPanel().getConfirmField().getPassword();

        if(pw.length == 0 && confirmPw.length == 0){
            this.getController().getSetupPanel().getMessageLabel().setText(" ");

        }else if(Arrays.equals(pw, confirmPw)){
            this.getController().getSetupPanel().getMessageLabel().setForeground(new Color(0, 150, 0));
            this.getController().getSetupPanel().getMessageLabel().setText("Password matches.");
        }else{
            this.getController().getSetupPanel().getMessageLabel().setForeground(Color.RED);
            this.getController().getSetupPanel().getMessageLabel().setText("Password does not match.");
        }
    }
}

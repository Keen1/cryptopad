package handlers.matcher;

import controllers.KeyStoreSetupController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;

public class PasswordMatchHandler implements DocumentListener {
    private KeyStoreSetupController controller;
    public PasswordMatchHandler(KeyStoreSetupController controller){
        this.controller = controller;
    }

    public KeyStoreSetupController getController(){
        return this.controller;
    }

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

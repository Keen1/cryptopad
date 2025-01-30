package handlers.matcher;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Arrays;

public class PasswordMatchHandler implements DocumentListener {
    private JPasswordField pwField;
    private JPasswordField confirmField;
    private JLabel messageLabel;
    public PasswordMatchHandler(JPasswordField pwField, JPasswordField confirmField, JLabel messageLabel){
        this.pwField = pwField;
        this.confirmField = confirmField;
        this.messageLabel = messageLabel;
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
        char[] pw = this.pwField.getPassword();
        char[] confirmPw = this.confirmField.getPassword();

        if(pw.length == 0 && confirmPw.length == 0){
            this.messageLabel.setText(" ");

        }else if(Arrays.equals(pw, confirmPw)){
            messageLabel.setForeground(Color.GREEN);
            messageLabel.setText("Passwords Match.");
        }else{
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Passwords do not match.");
        }
    }
}

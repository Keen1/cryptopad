package components;

import controllers.KeyStoreSetupController;
import controllers.LoginController;
import models.KeyStoreResultModel;

import javax.swing.*;
import java.awt.*;
import java.security.KeyStore;

public class LoginPanel extends JPanel {

    private JLabel loginLabel;
    private JPasswordField passwordField;
    private JButton submitButton;
    private JLabel messageLabel;
    LoginController controller;

    public LoginPanel(){
        this.controller = new LoginController(this);
        initComponents();
    }

    public LoginController getController(){
        return this.controller;
    }

    private void initSubmitButton(){
        this.submitButton = new JButton("Submit");
        this.submitButton.addActionListener(event ->{
           char[] pw = this.getPasswordField().getPassword();
           if(pw.length != 0){
                KeyStoreResultModel resultModel = this.getController().login(pw, "test_ks.jks");
                this.getMessageLabel().setText(resultModel.getMessage());


           }
        });
    }

    public JButton getSubmitButton(){
        if(this.submitButton == null){
            initSubmitButton();
        }
        return this.submitButton;
    }

    private void initLoginLabel(){
        this.loginLabel = new JLabel("Password:");
    }

    public JLabel getLoginLabel(){
        if(this.loginLabel == null){
            initLoginLabel();
        }
        return this.loginLabel;
    }
    private void initPasswordField(){
        this.passwordField = new JPasswordField(30);
    }

    public JPasswordField getPasswordField(){
        if(this.passwordField == null){
            initPasswordField();
        }
        return this.passwordField;
    }

    public void initMessageLabel(){
        this.messageLabel = new JLabel();
    }

    private JLabel getMessageLabel(){
        if(this.messageLabel == null){
            initMessageLabel();
        }
        return this.messageLabel;
    }

    private GridBagConstraints initGBC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        return gbc;
    }


    private void initComponents(){
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = initGBC();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        add(this.getLoginLabel(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(this.getPasswordField(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(this.getSubmitButton(), gbc);
        gbc.gridy = 2;
        add(this.getMessageLabel(), gbc);


    }

}

package controllers;

import components.LoginPanel;
import models.KeyStoreResultModel;
import util.KeyStoreFactory;

import java.security.KeyStore;

public class LoginController {
    private LoginPanel loginPanel;

    public LoginController(LoginPanel loginPanel){
        this.loginPanel = loginPanel;
    }

    public LoginPanel getLoginPanel(){
        return this.loginPanel;
    }

    public KeyStoreResultModel login(char[] pw, String path){
        return KeyStoreFactory.loadKeyStore(pw, path);
    }
}

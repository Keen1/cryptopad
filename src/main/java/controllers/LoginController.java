package controllers;

import components.LoginPanel;
import models.KeyStoreResultModel;
import util.KeyStoreFactory;

import java.security.KeyStore;
import java.util.function.Consumer;

public class LoginController {

    private final LoginPanel loginPanel;
    private final Consumer<KeyStoreResultModel> onLoginSuccess;

    public LoginController(LoginPanel loginPanel, Consumer<KeyStoreResultModel> onLoginSuccess){

        this.loginPanel = loginPanel;
        this.onLoginSuccess = onLoginSuccess;

    }

    public LoginPanel getLoginPanel(){

        return this.loginPanel;

    }

    private Consumer<KeyStoreResultModel> getCallBack(){

        return this.onLoginSuccess;

    }

    public void login(char[] pw, String path){

        KeyStoreResultModel result = KeyStoreFactory.loadKeyStore(pw, path);
        if(result.isSuccess()){
            this.getLoginPanel().updateMessageLabel(result.getMessage());
            this.getCallBack().accept(result);

        }else{
            this.getLoginPanel().updateMessageLabel(result.getMessage());
        }

    }
}

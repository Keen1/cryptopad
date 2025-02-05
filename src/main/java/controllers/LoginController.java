package controllers;

import components.LoginPanel;
import models.KeyStoreResultModel;
import util.KeyStoreFactory;

public class LoginController {

    private final LoginPanel loginPanel;
    private final Runnable onLoginSuccess;

    public LoginController(LoginPanel loginPanel, Runnable onLoginSuccess){

        this.loginPanel = loginPanel;
        this.onLoginSuccess = onLoginSuccess;

    }

    public LoginPanel getLoginPanel(){

        return this.loginPanel;

    }

    private Runnable getCallBack(){

        return this.onLoginSuccess;

    }

    public void login(char[] pw, String path){

        KeyStoreResultModel result = KeyStoreFactory.loadKeyStore(pw, path);
        if(result.isSuccess()){
            this.getLoginPanel().updateMessageLabel(result.getMessage());
            this.getCallBack().run();

        }else{
            this.getLoginPanel().updateMessageLabel(result.getMessage());
        }

    }
}

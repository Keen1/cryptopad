package controllers;

import components.LoginPanel;
import models.KeyStoreModel;
import models.KeyStoreResultModel;
import util.KeyStoreFactory;
import java.util.function.Consumer;

public class LoginController {

    private final LoginPanel loginPanel;
    private final Consumer<KeyStoreResultModel> onLoginSuccess;
    private final KeyStoreModel keyStoreModel;

    public LoginController(LoginPanel loginPanel, Consumer<KeyStoreResultModel> onLoginSuccess){

        this.loginPanel = loginPanel;
        this.onLoginSuccess = onLoginSuccess;
        this.keyStoreModel = new KeyStoreModel();

    }

    public LoginPanel getLoginPanel(){

        return this.loginPanel;

    }

    public KeyStoreModel getKeyStoreModel(){
        return this.keyStoreModel;
    }

    private Consumer<KeyStoreResultModel> getCallBack(){

        return this.onLoginSuccess;

    }

    public void login(char[] pw, String path){



        KeyStoreResultModel result = this.getKeyStoreModel().loadKeyStore(pw, path);

        if(result.isSuccess()){
            this.getLoginPanel().updateMessageLabel(result.getMessage());
            this.getCallBack().accept(result);

        }else{
            this.getLoginPanel().updateMessageLabel(result.getMessage());
        }

    }
}

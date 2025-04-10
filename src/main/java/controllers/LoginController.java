package controllers;

import components.LoginPanel;
import models.KeyStoreModel;
import models.KeyStoreResultModel;
import util.KeyStoreFactory;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.function.Consumer;

public class LoginController {

    private final LoginPanel loginPanel;
    private final Consumer<KeyStoreModel> onLoginSuccess;
    private final KeyStoreModel keyStoreModel;

    public LoginController(LoginPanel loginPanel, Consumer<KeyStoreModel> onLoginSuccess){

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

    private Consumer<KeyStoreModel> getCallBack(){

        return this.onLoginSuccess;

    }

    //this should be calling the keystore controller not the model
    //this means that the model should not be instantiated here it should either be passed and wrapped in the controller
    //or the controller should be passed itself

    public void login(char[] pw, String path){



        //KeyStoreResultModel result = this.getKeyStoreModel().loadKeyStore(pw, path);
        try{

            KeyStoreModel result = this.getKeyStoreModel().loadKeyStore(pw);
            //dont know if this call to setPw is required here. this is a new model object that is set outside of the
            //gui driver...we are doing the same thing in the setup controller where we are instantiating a new
            //model in the constructor...should we parameterize the model with the controller and keep instantiation to
            //the main driver? Seems like it would be cleaner

            //it also appears that im setting this in the loadKeyStore(pw) call when password is sent...
            result.setPw(pw);
            if(result != null){
                this.getLoginPanel().updateMessageLabel("Success!");
                this.getCallBack().accept(result);
            }
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e){
            this.getLoginPanel().updateMessageLabel(e.getMessage());
            System.out.printf("error: %s", e.getMessage());
        }


    }
}

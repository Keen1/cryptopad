package models;

import util.constants.AppConstants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreModel {
    private static final String KS_TYPE = "JCEKS";
    private static final String HOME_DIR = "user.home";

    public KeyStoreModel(){}

    public void generateKeyStore(char[] pw, String path){
        try(FileOutputStream outStream = new FileOutputStream(path)){
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            ks.load(null, null);
            ks.store(outStream, pw);

        }catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e){

        }
    }

    public KeyStoreResultModel loadKeyStore(char[] pw, String path){
        try{
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            try(FileInputStream inStream = new FileInputStream(path)){
                ks.load(inStream, pw);
                return new KeyStoreResultModel(ks, "Success", pw);
            }

        }catch(IOException |KeyStoreException | NoSuchAlgorithmException | CertificateException e){
            return new KeyStoreResultModel(null, e.getMessage());
        }
    }

    public void createKeyStore(char[] pw){
        try{
            Path cryptopadDir = Paths.get(System.getProperty(HOME_DIR), AppConstants.APP_FOLDER_NAME);
            Files.createDirectories(cryptopadDir);
            this.generateKeyStore(pw, AppConstants.KEYSTORE_PATH);
            
        }catch(IOException e){
            System.out.printf("error creating path: %s", e.getMessage());
        }
    }
}

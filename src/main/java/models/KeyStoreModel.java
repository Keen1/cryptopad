package models;

import util.constants.AppConstants;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
    private KeyStore ks;


    public KeyStoreModel(){}
    public KeyStoreModel(KeyStore ks){
        this.ks = ks;
    }

    public KeyStore getKeyStore(){
        return this.ks;
    }

    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }


    public void generateKeyStore(char[] pw, String path){
        try(FileOutputStream outStream = new FileOutputStream(path)){
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            ks.load(null, null);
            ks.store(outStream, pw);
            this.setKeyStore(ks);

        }catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e){
            System.out.printf("error: %s", e.getMessage());
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

    public void storeKey(SecretKey key, String alias){

    }

    public SecretKey generateKey(String cipher, int keyLength){
        SecretKey key = null;
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
            keyGen.init(keyLength);
            key = keyGen.generateKey();

        }catch(NoSuchAlgorithmException e){
            System.out.printf("error for %s cipher: %s", cipher, e.getMessage());
        }

        return key;

    }
}

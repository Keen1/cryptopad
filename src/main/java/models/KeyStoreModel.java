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
import java.util.Arrays;

public class KeyStoreModel {
    private static final String KS_TYPE = "JCEKS";
    private static final String HOME_DIR = "user.home";
    private KeyStore ks;
    private char[] pw;


    public KeyStoreModel(){}
    public KeyStoreModel(KeyStore ks, char[] pw){
        this.ks = ks;
        this.pw = pw;
    }

    public void setPw(char[] pw){
        this.pw = pw;
    }
    public char[] getPw(){
        return this.pw;
    }
    public void wipePw(){
        Arrays.fill(this.getPw(), (char) 0);
        this.setPw(null);
    }

    public KeyStore getKeyStore(){
        return this.ks;
    }

    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }

    public boolean hasKeyForAlias(String alias) throws KeyStoreException{
        return this.getKeyStore().containsAlias(alias);
    }


    private void generateKeyStore()throws KeyStoreException, IOException,
            NoSuchAlgorithmException,
            CertificateException{


        try(FileOutputStream outStream = new FileOutputStream(AppConstants.KEYSTORE_PATH)){
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            ks.load(null, null);
            ks.store(outStream, this.getPw());
            this.setKeyStore(ks);

        }
    }

    //TODO: this needs to be refactored and we need to rework our return object/ how this works to something else
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

    public void createKeyStore() throws Exception {

        if(this.getPw()!= null){
            Path cryptopadDir = Paths.get(System.getProperty(HOME_DIR), AppConstants.APP_FOLDER_NAME);
            Files.createDirectories(cryptopadDir);
            this.generateKeyStore();
        }

    }


    public SecretKey generateKey(String cipher, int keyLength) throws NoSuchAlgorithmException {

        SecretKey key = null;
        KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
        keyGen.init(keyLength);
        key = keyGen.generateKey();

        return key;

    }

    public void removeKey(String alias)throws KeyStoreException{


        this.getKeyStore().deleteEntry(alias);

    }

    private void saveStore(){
        try(FileOutputStream outStream = new FileOutputStream(AppConstants.KEYSTORE_PATH)){
            this.getKeyStore().store(outStream, this.getPw());
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e){
            System.out.printf("Error storing file: %s", e.getMessage());
        }
    }
}

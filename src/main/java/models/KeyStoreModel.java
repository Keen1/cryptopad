package models;

import util.constants.AppConstants;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
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



    public KeyStoreModel(char[] pw){

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


    private void generateKeyStore() throws KeyStoreException, IOException,
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
    public KeyStoreResultModel loadKeyStore(char[] pw, String path)
            throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException{

        if(this.getPw() != null){

            try(FileInputStream inStream = new FileInputStream(AppConstants.KEYSTORE_PATH)){

                KeyStore ks = KeyStore.getInstance(KS_TYPE);
                ks.load(inStream, this.getPw());
                return new KeyStoreResultModel(ks, "Success", this.getPw());

            }

        }
        return null;
    }

    public KeyStoreModel loadKeyStore(char[] pw) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException{

        try(FileInputStream inStream = new FileInputStream(AppConstants.KEYSTORE_PATH)){

            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            ks.load(inStream, pw);

            if(this.getKeyStore() == null){
                this.setKeyStore(ks);

            }

            if(this.getPw() == null){
                this.setPw(pw);

            }

            return this;

        }
    }

    public void loadKeyStore()
            throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException {

        try(FileInputStream inStream = new FileInputStream(AppConstants.KEYSTORE_PATH)){
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            ks.load(inStream, this.getPw());
            this.setKeyStore(ks);

        }
    }

    public void createKeyStore() throws Exception {

        if(this.getPw()!= null){
            Path cryptopadDir = Paths.get(System.getProperty(HOME_DIR), AppConstants.APP_FOLDER_NAME);
            Files.createDirectories(cryptopadDir);
            this.generateKeyStore();
        }

    }

    public void storeKey(SecretKey key, String alias) throws KeyStoreException {

        if(this.getPw() != null){

            this.getKeyStore().setKeyEntry(alias, key, this.getPw(), null);

        }

    }

    public SecretKey getKey(String alias, char[] pw)
            throws KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException {

        if(this.getPw() != null){

            return (SecretKey)this.getKeyStore().getKey(alias, this.getPw());

        }

        return null;
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

    public void saveKeyStore()throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {

        if(this.getPw() != null){

            try(FileOutputStream outStream = new FileOutputStream(AppConstants.KEYSTORE_PATH)) {

                this.getKeyStore().store(outStream, this.getPw());

            }
        }

    }
}

package models;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreModel {
    private static final String KS_TYPE = "JCEKS";

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
}

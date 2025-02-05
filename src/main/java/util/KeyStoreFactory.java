package util;

import models.KeyStoreResultModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class KeyStoreFactory {
    private static final String KS_TYPE = "JCEKS";
    private KeyStoreFactory(){

    }
    public static void generateKeyStore(char[] pw, String path){
        try(FileOutputStream outStream = new FileOutputStream(path)){
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            ks.load(null, null);
            ks.store(outStream, pw);
            System.out.printf("keystore created successfully: %s", path);


        }catch(KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e){
            System.out.printf("Error creating the keystore: %s", e.getMessage());
        }
    }

    public static KeyStoreResultModel loadKeyStore(char[] pw, String path){
        try{
            KeyStore ks = KeyStore.getInstance(KS_TYPE);
            try(FileInputStream inStream = new FileInputStream(path)){
                ks.load(inStream, pw);
                return new KeyStoreResultModel(ks, "Success!");
            }
        }catch(KeyStoreException | NoSuchAlgorithmException | CertificateException e){
            return new KeyStoreResultModel(null, e.getMessage());
        }catch(IOException e){
            return new KeyStoreResultModel(null, e.getMessage());
        }
    }


}

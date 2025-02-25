package controllers;

import util.constants.AppConstants;
import util.enums.CipherBlockSizes;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Enumeration;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;

public class SecretKeyController {
    private KeyStore ks;
    private char[] pw;
    private static final String KEYSTORE_PATH = AppConstants.KEYSTORE_PATH;
    private final MainPanelController mainPanelController;



    public SecretKeyController(KeyStore ks, char[] pw, MainPanelController mainPanelController){
        this.ks = ks;
        this.pw = pw;
        this.mainPanelController = mainPanelController;
    }

    public MainPanelController getMainPanelController() {
        return this.mainPanelController;
    }

    private char[] getPw(){
        return this.pw;
    }
    public void setPw(char[] pw){
        this.pw = pw;
    }

    public void wipePw(){
        Arrays.fill(this.getPw(), (char) 0);
    }

    public void storeKey(SecretKey key, String alias) throws KeyStoreException {
        try{
            this.getKeyStore().setKeyEntry(alias, key, this.getPw(), null);
            this.getMainPanelController().updateStatus(String.format("Success storing key. Alias: %s",alias));

            this.saveStore();

        }catch(KeyStoreException e){
            this.getMainPanelController().updateStatus(String.format("Error accessing keystore: %s", e.getMessage()));
            throw new KeyStoreException(e);
        }
    }

    public SecretKey getKey(String alias){
        SecretKey key = null;
        try{
            key = (SecretKey)this.getKeyStore().getKey(alias, this.getPw());
        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            this.getMainPanelController().updateStatus(String.format("Error retrieving key: %s Alias: %s", e.getMessage(), alias));
        }
        return key;

    }



    public boolean hasKeyForAlias(String alias) throws KeyStoreException {
        return this.getKeyStore().containsAlias(alias);
    }

    //this is dependent on the block size of the chosen algorithm
    public IvParameterSpec initIv(String algorithm){
        int size = CipherBlockSizes.fromString(algorithm).getIvSize();
        byte[] iv = new byte[size];
        SecureRandom secureRand = new SecureRandom();
        secureRand.nextBytes(iv);
        return new IvParameterSpec(iv);

    }

    public SecretKey generateKey(String cipher, int keyLength){
        SecretKey key = null;
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
            keyGen.init(keyLength);
            key = keyGen.generateKey();

        }catch(NoSuchAlgorithmException e){
            this.getMainPanelController().updateStatus(String.format("error for %s cipher: %s", cipher, e.getMessage()));
        }
        return key;
    }

    public void removeKey(String alias){
        try{
            this.getKeyStore().deleteEntry(alias);

        }catch(KeyStoreException e){
            this.getMainPanelController().updateStatus(String.format("Error removing key: %s", e.getMessage()));
        }
    }






    public void setKeyStore(KeyStore ks){
        this.ks = ks;
    }
    public KeyStore getKeyStore(){
        return this.ks;
    }

    public void showAliases(){
        try{
            Enumeration<String> aliases = this.getKeyStore().aliases();
            while(aliases.hasMoreElements()){
                System.out.printf("Entry alias: %s\n", aliases.nextElement());
            }


        }catch(KeyStoreException e){
            System.out.println("Error printing key aliases");
        }

    }

    private void saveStore(){
        try(FileOutputStream outStream = new FileOutputStream(KEYSTORE_PATH)){
            this.getKeyStore().store(outStream, this.getPw());
        }catch(IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e){
            System.out.printf("Error storing file: %s\n", e.getMessage());
        }
    }
}

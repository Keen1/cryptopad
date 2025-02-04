package crypters;

import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.KeyStore.SecretKeyEntry;

public class KeyStoreBuilder {
    private static final String KS_TYPE = "JCEKS";
    private char[] pw;
    private KeyStore ks;
    public KeyStoreBuilder(char[] pw){
        this.pw = pw;
    }

    public void setKeyStore(KeyStore ks){
        this.ks = ks;

    }
    public KeyStore getKeyStore(){
        return this.ks;
    }

    public void setPw(char[] pw){
        this.pw = pw;
    }
    public char[] getPw(){
        return this.pw;
    }
    public void generateKeystore(char[] pw) throws Exception{
        KeyStore ks = KeyStore.getInstance(KS_TYPE);
        ks.load(null, null);
        try(FileOutputStream outStream = new FileOutputStream("test_ks.jks")){
            ks.store(outStream, pw);
            System.out.println("Keystore created successfully: test_ks.jks");
        }

    }

    public void storeKey(String alias, SecretKey key) throws Exception{
        SecretKeyEntry entry = new SecretKeyEntry(key);
        this.getKeyStore().setEntry(alias, entry, null);
    }

    public void saveKeyStore(String path) throws Exception{

        try(FileOutputStream outStream = new FileOutputStream(path)){
            this.getKeyStore().store(outStream, this.getPw());
        }
    }


}

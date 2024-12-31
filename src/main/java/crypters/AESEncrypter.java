package crypters;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

class AESEncrypter implements Crypter{
    private final String path;

    public AESEncrypter(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
    @Override
    public boolean run() {

        return true;
    }

    @Override
    public IvParameterSpec getIvParameters() {
        return null;
    }

    @Override
    public Cipher getCipher() {
        return null;
    }


}

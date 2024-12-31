package crypters;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;

interface Crypter {
    boolean run();
    IvParameterSpec getIvParameters();
    Cipher getCipher();

}

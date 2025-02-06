package util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.Set;

public class CipherTest {
    public CipherTest(){}

    public void testCipherSuite(){
        Security.addProvider(new BouncyCastleProvider());
        Set<String> ciphers = Security.getAlgorithms("Cipher");

        ciphers.stream().sorted().forEach(System.out::println);

    }
}

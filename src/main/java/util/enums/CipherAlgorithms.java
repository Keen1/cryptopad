package util.enums;

import java.util.Arrays;
import java.util.List;

public enum CipherAlgorithms {
    AES(Arrays.asList(128,192,256)),
    ARIA(Arrays.asList(128,192,256)),
    BLOWFISH(Arrays.asList(32,64,256,448)),
    CAMELLIA(Arrays.asList(128,192,256)),
    CAST5(Arrays.asList(40,64,80,96,112,128)),
    CAST6(Arrays.asList(120,160,192,224,256)),
    DSTU7624(Arrays.asList(128,256,512)),
    GOST3412_2015(List.of(256)),
    IDEA(List.of(128)),
    NOEKEON(List.of(128)),
    SERPENT(Arrays.asList(128,192,256)),
    SM4(List.of(128)),
    THREEFISH_1024(List.of(1024)),
    TWOFISH(Arrays.asList(128,192,256)),
    XTEA(List.of(128));

    private final List<Integer> supportedKeyLengths;

    CipherAlgorithms(List<Integer> supportedKeyLengths){
        this.supportedKeyLengths = supportedKeyLengths;
    }

    public List<Integer> getSupportedKeyLengths(){
        return this.supportedKeyLengths;
    }

    public static String[] getNames(){
        return Arrays.stream(values()).map(cipher -> cipher.name().replace("_", "-"))
                .toArray(String[]::new);
    }

    public static CipherAlgorithms fromString(String name){
        return valueOf(name.replace("-", "_"));
    }

}

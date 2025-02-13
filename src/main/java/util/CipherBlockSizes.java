package util;

public enum CipherBlockSizes {
    THREEFISH(128),
    AES(16),
    CAMIELLIA(16),
    CAST6(16),
    DSTU7624(16),
    GOST3412_2015(16),
    NOEKEON(16),
    SERPENT(16),
    SM4(16),
    TWOFISH(16),
    ARIA(16),
    BLOWFISH(8),
    CAST5(8),
    IDEA(8),
    XTEA(8);
    private final int ivSize;
    CipherBlockSizes(int ivSize){
        this.ivSize = ivSize;
    }
    public int getIvSize(){
        return this.ivSize;
    }

    public static CipherBlockSizes fromString(String algorithm){
        return valueOf(algorithm.replace("-", "_"));
    }

}

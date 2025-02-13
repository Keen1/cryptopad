package util.enums;

import java.util.Arrays;

public enum CipherBlockModes {
    CBC,
    CFB,
    CTR,
    ECB,
    GCM,
    OFB;

    public static String[] getNames(){
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}

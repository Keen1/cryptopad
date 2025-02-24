package util.enums;

import java.util.Arrays;

public enum CipherPaddingModes {
    PKCS5Padding,
    PKCS7Padding,
    NoPadding;

    public static String[] getNames(){
        return Arrays.stream(values()).map(Enum::name).toArray(String[]::new);
    }
}

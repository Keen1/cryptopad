package util.constants;

import java.nio.file.Paths;

public class AppConstants {
    public static final String APP_FOLDER_NAME = ".cryptopad";
    public static final String KEYSTORE_FILE_NAME = "keystore.jks";
    public static final String KEYSTORE_PATH = Paths.get(System.getProperty("user.home"), APP_FOLDER_NAME, KEYSTORE_FILE_NAME).toString();

}

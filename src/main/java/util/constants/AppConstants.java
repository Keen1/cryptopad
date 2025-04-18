package util.constants;

import java.nio.file.Paths;

public class AppConstants {
    public static final String HOME_DIR = "user.home";
    public static final String APP_FOLDER_NAME = ".cryptopad";
    public static final String KEYSTORE_FILE_NAME = "keystore.jks";
    public static final String PREFERENCES_FILE_NAME = "preferences.json";
    public static final String KEYSTORE_PATH = Paths.get(System.getProperty("user.home"), APP_FOLDER_NAME, KEYSTORE_FILE_NAME).toString();
    public static final String PREFERENCES_PATH = Paths.get(System.getProperty("user.home"), APP_FOLDER_NAME, PREFERENCES_FILE_NAME).toString();


}

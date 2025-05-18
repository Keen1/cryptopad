package drivers;

import components.frames.MainFrame;
import components.panels.KeyStoreSetupPanel;
import components.panels.LoginPanel;
import components.panels.MainPanel;
import controllers.PreferencesController;
import models.KeyStoreModel;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.constants.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Security;


public class GuiDriver {
    private static JFrame frame;
    private static final Dimension MAIN_DIM = new Dimension(800,1000);
    private static final Dimension KEYSTORE_SETUP_DIM = new Dimension(400, 600);
    private static final Dimension LOGIN_DIM = new Dimension(400, 200);
    private static final String KEYSTORE_PATH = AppConstants.KEYSTORE_PATH;

    public static void main(String [] args){

        Security.addProvider(new BouncyCastleProvider());
        setApplicationName();


        SwingUtilities.invokeLater(() ->{
            if(!keystoreExists()){
                initDirectoryAndPreferences();
            }
            MainFrame mainFrame = new MainFrame();
            mainFrame.initialize();
        });




    }
    private static void initDirectoryAndPreferences(){

        Path cryptopadDir = Paths.get(System.getProperty(AppConstants.HOME_DIR), AppConstants.APP_FOLDER_NAME);
        Path preferencesPath = Paths.get(AppConstants.PREFERENCES_PATH);


        try{
            Files.createDirectory(cryptopadDir);
            Files.createFile(preferencesPath);

        }catch(IOException e){
            System.out.printf("error creating directory or preferences file: %s", e.getMessage());
        }

    }

    private static boolean keystoreExists(){
        return Files.exists(Paths.get(KEYSTORE_PATH));
    }

    private static void setApplicationName(){
        try{
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            java.lang.reflect.Field awtAppClassNameField = toolkit.getClass().getDeclaredField("awtAppClassName");
            awtAppClassNameField.setAccessible(true);
            awtAppClassNameField.set(toolkit, "cryptopad");
        }catch(NoSuchFieldException e){
            System.out.printf("no field name: %s", e.getMessage());
        }catch(IllegalAccessException e){
            System.out.printf("illegal access: %s", e.getMessage());
        }
    }



}

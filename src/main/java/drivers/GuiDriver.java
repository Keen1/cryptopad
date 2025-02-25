package drivers;

import components.KeyStoreSetupPanel;
import components.LoginPanel;
import components.MainPanel;
import models.KeyStoreResultModel;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import util.constants.AppConstants;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
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
        SwingUtilities.invokeLater(() ->{
            initFrame();
            checkAndShowInitialPanel();
        });




    }
    private static void checkAndShowInitialPanel(){
        if(keystoreExists()){
            showLoginPanel();

        }else{
            showKeystoreSetupPanel();
        }
    }

    private static boolean keystoreExists(){
        return Files.exists(Paths.get(KEYSTORE_PATH));
    }
    private static void initFrame(){
        frame = new JFrame("cryptopad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLaf(frame);

    }

    private static void showKeystoreSetupPanel(){
        frame.setPreferredSize(KEYSTORE_SETUP_DIM);
        KeyStoreSetupPanel setupPanel = new KeyStoreSetupPanel();
        setupPanel.getController().setOnSetupComplete(GuiDriver::showLoginPanel);
        frame.setContentPane(setupPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void showLoginPanel(){
        frame.setPreferredSize(LOGIN_DIM);
        frame.setContentPane(new LoginPanel(GuiDriver::showMainPanel));
        frame.pack();
        frame.setVisible(true);
    }


    public static void showMainPanel(KeyStoreResultModel model){
        frame.setPreferredSize(MAIN_DIM);
        frame.setContentPane(new MainPanel(model));
        frame.pack();
        frame.setLocationRelativeTo(null);
    }


    public static void setLaf(JFrame frame){

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(frame);
        }catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
               IllegalAccessException e){
            System.out.printf("Unsupported look and feel: %s\n", "Test");
            System.out.printf("Error: %s", e.getMessage());
        }

    }
}

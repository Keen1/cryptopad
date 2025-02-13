package drivers;

import components.LoginPanel;
import components.MainPanel;
import models.KeyStoreResultModel;
import util.CipherTest;

import javax.swing.*;
import java.awt.*;
import java.security.KeyStore;


public class GuiDriver {
    private static JFrame frame;
    private static final Dimension MAIN_DIM = new Dimension(800,1000);
    private static final Dimension KEYSTORE_SETUP_DIM = new Dimension(400, 200);

    public static void main(String [] args){
        SwingUtilities.invokeLater(() ->{
            initFrame();
            showLoginPanel();
        });




    }
    private static void initFrame(){
        frame = new JFrame("cryptopad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLaf(frame);

    }

    public static void showLoginPanel(){
        frame.setPreferredSize(KEYSTORE_SETUP_DIM);
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

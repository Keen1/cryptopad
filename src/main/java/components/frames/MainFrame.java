package components.frames;

import components.panels.KeyStoreSetupPanel;
import components.panels.LoginPanel;
import components.panels.MainPanel;
import models.KeyStoreModel;
import util.constants.AppConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class MainFrame extends JFrame {

    private static final Dimension DEFAULT_DIM = new Dimension(800, 1000);
    private static final Dimension KEYSTORE_SETUP_DIM = new Dimension(400,600);
    private static final Dimension LOGIN_DIM = new Dimension(400,200);

    private KeyStoreSetupPanel keyStoreSetupPanel;
    private LoginPanel loginPanel;
    private MainPanel mainPanel;

    public MainFrame(){
        super("cryptopad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLaf();
        setFrameIcon();


    }

    public void initialize(){
        if(keyStoreExists()){
            showLoginPanel();
        }else{
            showKeyStoreSetupPanel();
        }
    }

    private boolean keyStoreExists(){
        return Files.exists(Paths.get(AppConstants.KEYSTORE_PATH));
    }

    public void showKeyStoreSetupPanel(){
        setPreferredSize(KEYSTORE_SETUP_DIM);
        KeyStoreSetupPanel setupPanel = new KeyStoreSetupPanel();
        setupPanel.getController().setOnSetupComplete(this::showLoginPanel);
        setContentPane(setupPanel);
        pack();
        setVisible(true);
    }

    public void showLoginPanel(){
        setPreferredSize(LOGIN_DIM);
        setContentPane(new LoginPanel(this::showMainPanel));
        pack();
        setVisible(true);
    }

    public void showMainPanel(KeyStoreModel model){
        setPreferredSize(DEFAULT_DIM);
        setContentPane(new MainPanel(model));
        pack();
        setLocationRelativeTo(null);
    }

    private void setLaf(){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }catch(UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e){
            System.out.printf("unsupported look and feel: %s", e.getMessage());
        }
    }

    private void setFrameIcon(){
        URL url = getClass().getResource("/icon.png");
        Image logo = null;
        try{
            logo = ImageIO.read(Objects.requireNonNull(url));
        }catch(IOException e){
            System.out.printf("error setting program icon image: %s", e.getMessage());
        }

        if(logo != null){
            setIconImage(logo);
        }
    }

}

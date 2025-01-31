package gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import components.KeyStoreSetupPanel;
import javax.swing.*;
import java.awt.*;


public class GuiDriver {
    public static void main(String [] args){

        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("Setup");
            frame.setPreferredSize(new Dimension(400, 600));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            KeyStoreSetupPanel ksSetupPanel = new KeyStoreSetupPanel();
            frame.setContentPane(ksSetupPanel);

            //LoginPanel loginPanel = new LoginPanel();
            //frame.setContentPane(loginPanel);

            //DirectorySetupPanel dirSetupPanel = new DirectorySetupPanel();
            //frame.setContentPane(dirSetupPanel);
            setLaf(frame);
            frame.pack();
            frame.setVisible(true);

        });

        //SwingUtilities.invokeLater(Gui::new);

    }

    public static void setLaf(JFrame frame){

        LookAndFeel lightLaf = new FlatLightLaf();
        LookAndFeel darkLaf = new FlatDarkLaf();
        try{
            UIManager.setLookAndFeel(darkLaf);
            SwingUtilities.updateComponentTreeUI(frame);
        }catch(UnsupportedLookAndFeelException e){
            System.out.printf("Unsupported look and feel: %s\n", lightLaf.getName());
            System.out.printf("Error: %s", e.getMessage());
        }

    }
}

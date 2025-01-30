package gui;

import com.formdev.flatlaf.FlatLightLaf;
import components.KeyStoreSetupPanel;
import components.LoginPanel;

import javax.swing.*;
import java.awt.*;


public class GuiDriver {
    public static void main(String [] args){

        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("Setup");
            frame.setPreferredSize(new Dimension(400, 400));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //KeyStoreSetupPanel ksSetupPanel = new KeyStoreSetupPanel();
            //frame.setContentPane(ksSetupPanel);

            LoginPanel loginPanel = new LoginPanel();
            frame.setContentPane(loginPanel);
            setLaf(frame);
            frame.pack();
            frame.setVisible(true);

        });

        //SwingUtilities.invokeLater(Gui::new);

    }

    public static void setLaf(JFrame frame){

        LookAndFeel lightLaf = new FlatLightLaf();
        try{
            UIManager.setLookAndFeel(lightLaf);
            SwingUtilities.updateComponentTreeUI(frame);
        }catch(UnsupportedLookAndFeelException e){
            System.out.printf("Unsupported look and feel: %s", lightLaf.getName());
        }

    }
}

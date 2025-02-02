package gui;

import components.KeyStoreSetupPanel;

import javax.swing.*;
import java.awt.*;


public class GuiDriver {

    public static void main(String [] args){
        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("Setup");
            frame.setPreferredSize(new Dimension(400, 600));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            setLaf(frame);

            KeyStoreSetupPanel ksSetupPanel = new KeyStoreSetupPanel();
            frame.setContentPane(ksSetupPanel);

            //LoginPanel loginPanel = new LoginPanel();
            //frame.setContentPane(loginPanel);


            frame.pack();
            frame.setVisible(true);

        });

        //SwingUtilities.invokeLater(Gui::new);

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

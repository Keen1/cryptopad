package gui;

import components.KeyStoreSetupPanel;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.swing.*;
import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class GuiDriver {
    public static void main(String [] args){

        SwingUtilities.invokeLater(() ->{
            JFrame frame = new JFrame("Setup");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            KeyStoreSetupPanel ksSetupPanel = new KeyStoreSetupPanel();
            frame.setContentPane(ksSetupPanel);
            frame.pack();
            frame.setVisible(true);
        });
        //SwingUtilities.invokeLater(Gui::new);

    }
}

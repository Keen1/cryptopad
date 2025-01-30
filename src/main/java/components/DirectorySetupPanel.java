package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class DirectorySetupPanel extends JPanel {
    private JTextField dirField;
    private JButton browseBtn;
    private JButton confirmBtn;
    private JLabel messageLabel;
    private File selDir;
    private boolean isConfigSet;

    public DirectorySetupPanel(){}

    private void initializeComponents(){
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,20,20));
        GridBagConstraints gbc = initGBC();


    }

    private void initBrowseBtn(){
        this.browseBtn = new JButton("Browse");

    }
    public JButton getBrowseBtn(){
        if(this.browseBtn == null){
            initBrowseBtn();
        }
        return this.browseBtn;
    }
    private void initConfirmBtn(){
        this.confirmBtn = new JButton("Confirm");
    }
    public JButton getConfirmBtn(){
        if(this.confirmBtn == null){
            initConfirmBtn();
        }
        return this.confirmBtn;
    }

    private void initDirField(){
        this.dirField = new JTextField(30);

    }
    public JTextField getDirField(){
        if(this.dirField == null){
            initDirField();
        }
        return this.dirField;
    }

    private GridBagConstraints initGBC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        return gbc;
    }

    private void initMessageLabel(){
        this.messageLabel = new JLabel("Select a directory for program files.");

    }

    public JLabel getMessageLabel(){
        if(this.messageLabel == null){
            initMessageLabel();
        }
        return this.messageLabel;
    }

}

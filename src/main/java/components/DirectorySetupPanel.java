package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DirectorySetupPanel extends JPanel {
    private JTextField dirField;
    private JButton browseBtn;
    private JButton confirmBtn;
    private JLabel messageLabel;
    private File selDir;
    private boolean isConfigSet;

    public DirectorySetupPanel(){
        initializeComponents();
    }

    private void initializeComponents(){
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20,20,20,20));
        GridBagConstraints gbc = initGBC();
        add(getDirField(), gbc);
        add(getBrowseBtn(), gbc);
        add(getConfirmBtn(), gbc);
        add(getMessageLabel(), gbc);


    }
    public File getSelDir(){
        return this.selDir;
    }
    public void setSelDir(File selDir){
        this.selDir = selDir;
    }

    private void initBrowseBtn(){
        this.browseBtn = new JButton("Browse");
        this.browseBtn.addActionListener(e -> {
            File file = chooseProgDir();
            setDirFieldText(file.getAbsolutePath());
        });

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

    public void setDirFieldText(String path){
        this.getDirField().setText(path);
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

    public File chooseProgDir(){
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("choose a directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int res = fileChooser.showOpenDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
            file = fileChooser.getSelectedFile();
        }
        return file;

    }

    public boolean confirmPath(String path){

        if(path.isEmpty()){
            return false;
        }

        File dir = new File(path);

        if(!dir.exists()){
            return false;
        }

        return true;
    }

}

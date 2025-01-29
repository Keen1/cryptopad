package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class KeyStoreSetupPanel extends JPanel{
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel messageLabel;
    private JButton confirmBtn;
    private boolean isPwSet = false;

    public KeyStoreSetupPanel(){
        initializeComponents();
    }

    private void initializeComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = initGBC();
        JLabel instructLabel = initInstructionLabel();
        add(instructLabel, gbc);
        add(this.getPasswordField(), gbc);
        add(this.getConfirmField(), gbc);
        setGBCForBtn(gbc);

        add(this.getConfirmBtn(), gbc);

    }

    private GridBagConstraints initGBC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);

        return gbc;
    }

    private void setGBCForBtn(GridBagConstraints gbc){
        gbc.fill = GridBagConstraints.NONE;
    }

    private JLabel initInstructionLabel(){
        return new JLabel("<html>Please set a master password for your keystore.<br><br>" +
                "Requirements:<br>" +
                "• Minimum 12 characters<br>" +
                "• At least one uppercase letter<br>" +
                "• At least one lowercase letter<br>" +
                "• At least one number<br>" +
                "• At least one special character</html>");
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

    private void initPasswordField(){
        this.passwordField = new JPasswordField(20);

    }

    private void initConfirmField(){
        this.confirmField = new JPasswordField(20);

    }

    public JPasswordField getPasswordField(){
        if(this.passwordField == null){
            initPasswordField();
        }
        return this.passwordField;
    }

    public JPasswordField getConfirmField(){
        if(this.confirmField == null){
            initConfirmField();
        }
        return this.confirmField;
    }
}

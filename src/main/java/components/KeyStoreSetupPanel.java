package components;

import handlers.matcher.PasswordMatchHandler;
import handlers.matcher.PasswordReqMatchHandler;

import javax.swing.*;
import java.awt.*;

public class KeyStoreSetupPanel extends JPanel{
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel messageLabel;
    private JLabel pwReqFeedbackLabel;
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

        gbc.fill = GridBagConstraints.NONE;
        add(this.getConfirmBtn(), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weighty = 1;
        add(createFeedbackPanel(), gbc);
        //add(this.getMessageLabel(), gbc);
        //add(this.getPwReqFeedbackLabel(), gbc);
        initMatchingHandler();
        initReqCheckHandler();

    }

    private JPanel createFeedbackPanel(){
        JPanel feedbackPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        feedbackPanel.add(this.getMessageLabel(), gbc);
        feedbackPanel.add(this.getPwReqFeedbackLabel(), gbc);
        return feedbackPanel;
    }
    private void initMatchingHandler(){
        PasswordMatchHandler matchHandler = new PasswordMatchHandler(this.getPasswordField(), this.getConfirmField(), this.getMessageLabel());
        this.getPasswordField().getDocument().addDocumentListener(matchHandler);
        this.getConfirmField().getDocument().addDocumentListener(matchHandler);
    }

    private void initReqCheckHandler(){
        PasswordReqMatchHandler reqMatchHandler = new PasswordReqMatchHandler(this.getPasswordField(), this.getPwReqFeedbackLabel());
        this.getPasswordField().getDocument().addDocumentListener(reqMatchHandler);
    }

    private GridBagConstraints initGBC(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
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

    private void initPwReqFeedbackLabel(){
        this.pwReqFeedbackLabel = new JLabel();
    }

    public JLabel getPwReqFeedbackLabel(){
        if(this.pwReqFeedbackLabel == null){
            initPwReqFeedbackLabel();
        }
        return this.pwReqFeedbackLabel;
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

    private void initMessageLabel(){
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setForeground(Color.RED);

    }

    public JLabel getMessageLabel(){
        if(this.messageLabel == null){
            initMessageLabel();
        }
        return this.messageLabel;
    }
}


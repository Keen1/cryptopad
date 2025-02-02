package components;

import controllers.KeyStoreSetupController;
import handlers.matcher.PasswordMatchHandler;
import handlers.matcher.PasswordReqMatchHandler;

import javax.swing.*;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.Highlighter;
import java.awt.*;

public class KeyStoreSetupPanel extends JPanel{
    private JPasswordField passwordField;
    private JPasswordField confirmField;
    private JLabel messageLabel;
    private JLabel pwReqFeedbackLabel;
    private JButton confirmBtn;
    private KeyStoreSetupController controller;

    public KeyStoreSetupPanel(){
        this.controller = new KeyStoreSetupController(this);

        initializeComponents();
    }

    public KeyStoreSetupController getController(){
        return this.controller;
    }

    private void initializeComponents(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = initGBC();
        JLabel instructLabel = initInstructionLabel();
        add(instructLabel, gbc);
        gbc.ipady = 10;
        add(this.getPasswordField(), gbc);
        add(this.getConfirmField(), gbc);
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;
        add(this.getConfirmBtn(), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.weighty = 1;
        add(createFeedbackPanel(), gbc);
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
        PasswordMatchHandler matchHandler = new PasswordMatchHandler(this.getController());
        this.getPasswordField().getDocument().addDocumentListener(matchHandler);
        this.getConfirmField().getDocument().addDocumentListener(matchHandler);
    }

    private void initReqCheckHandler(){
        PasswordReqMatchHandler reqMatchHandler = new PasswordReqMatchHandler(this.getController());
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
        this.confirmBtn.setFocusPainted(false);
        this.confirmBtn.addActionListener(event -> {
           if(this.getController().areRequirementsMet()){
               System.out.println("Requirements met");
           }else{
               System.out.println("Requirements not met");
           }
        });
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


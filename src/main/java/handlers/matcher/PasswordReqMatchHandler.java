package handlers.matcher;

import controllers.KeyStoreSetupController;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
* password requirement match handler - checks the password to ensure it meets the defined requirements for the program
* provides visual feedback via foreground coloring of requirements
*/

public class PasswordReqMatchHandler implements DocumentListener {

    //requires the controller for the keystore setup panel
    KeyStoreSetupController controller;


    //constructor
    public PasswordReqMatchHandler(KeyStoreSetupController controller){
        this.controller = controller;
    }

    //getter for controller
    public KeyStoreSetupController getController(){
        return this.controller;
    }

    //overrides for the insert, remove, and change methods
    @Override
    public void insertUpdate(DocumentEvent e) {
        checkRequirements();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        checkRequirements();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        checkRequirements();
    }


    //check the password against the requirements provided
    public void checkRequirements(){

        char[] pw = this.getController().getSetupPanel().getPasswordField().getPassword();

        if(pw.length == 0){
            this.getController().getSetupPanel().getPwReqFeedbackLabel().setText("");
            return;
        }

        boolean[] requirements = new boolean[5];

        requirements[0] = pw.length >= 12;
        requirements[1] = hasUpperCase(pw);
        requirements[2] = hasLowerCase(pw);
        requirements[3] = hasNumber(pw);
        requirements[4] = hasSpecial(pw);

        updateRequirementsList(requirements);
        this.getController().setRequirementsMet(allRequirementsMet(requirements));
    }

    //check if all the requirements have been met
    public boolean allRequirementsMet(boolean[] requirements){

        for(boolean b : requirements){
            if(!b){
                return false;
            }
        }
        return true;
    }

    //update the requirements list visual feedback depending of if the requirement was met or not
    private void updateRequirementsList(boolean[] requirements){

        StringBuilder html = new StringBuilder("<html>");

        String[] reqDescriptions ={
                "Minimum 12 characters",
                "At least one uppercase letter",
                "At least one lowercase letter",
                "At least one number",
                "At least one special character"
        };

        for(int i = 0; i < requirements.length; i++){

            String color = requirements[i] ? "green": "red";
            html.append(String.format("<font color = '%s'>%s</font><br>", color, reqDescriptions[i]));

        }

        html.append("</html>");
        this.getController().getSetupPanel().getPwReqFeedbackLabel().setText(html.toString());
    }

    //one uppercase character requirement
    private boolean hasUpperCase(char[] pw){

        for(char c : pw){
            if(Character.isUpperCase(c)){
                return true;
            }
        }

        return false;

    }

    //one lowercase character requirement
    private boolean hasLowerCase(char[] pw){

        for(char c : pw){
            if(Character.isLowerCase(c)){
                return true;
            }
        }

        return false;

    }

    //one number character requirement
    private boolean hasNumber(char[] pw){

        for(char c : pw){
            if(Character.isDigit(c)){
                return true;
            }
        }

        return false;

    }

    //one special character requirement
    private boolean hasSpecial(char[] pw){

        for(char c : pw){
            if(!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)){
                return true;
            }
        }

        return false;

    }


}

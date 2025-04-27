package handlers.preferences;

import controllers.PreferencesController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*
* Preferences Dialog Handler -- handles user applying preferences in the preferences dialog
*/
public class PreferencesDialogHandler implements ActionListener {

    //controller
    private final PreferencesController controller;

    //constructor
    public PreferencesDialogHandler(PreferencesController controller){
        this.controller = controller;
    }

    //override of action performed, pulls preferences from the dialog then saves them, then sets them
    //TODO -- this might need access to the frame if we want to update the component tree from here.
    @Override
    public void actionPerformed(ActionEvent event){

        String selectedTheme = this.getController().getPreferencesDialog().getSelectedTheme();
        String selectedFontFamily = this.getController().getPreferencesDialog().getSelectedFontFamily();
        int selectedFontSize = this.getController().getPreferencesDialog().getSelectedFontSize();

        this.getController().savePreferences(selectedTheme, selectedFontFamily, selectedFontSize);

    }

    //getter for the dialog controller
    public PreferencesController getController(){
        return this.controller;
    }

}

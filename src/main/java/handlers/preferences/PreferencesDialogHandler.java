package handlers.preferences;

import controllers.PreferencesController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreferencesDialogHandler implements ActionListener {
    private final PreferencesController controller;

    public PreferencesDialogHandler(PreferencesController controller){
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent event){

        String selectedTheme = this.getController().getPreferencesDialog().getSelectedTheme();
        String selectedFontFamily = this.getController().getPreferencesDialog().getSelectedFontFamily();
        int selectedFontSize = this.getController().getPreferencesDialog().getSelectedFontSize();

        this.getController().savePreferences(selectedTheme, selectedFontFamily, selectedFontSize);
    }

    public PreferencesController getController(){
        return this.controller;
    }

}

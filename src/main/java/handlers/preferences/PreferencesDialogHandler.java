package handlers.preferences;

import controllers.PreferencesController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreferencesDialogHandler implements ActionListener {
    private PreferencesController controller;

    public PreferencesDialogHandler(PreferencesController controller){
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent event){}

    public PreferencesController getController(){
        return this.controller;
    }

}

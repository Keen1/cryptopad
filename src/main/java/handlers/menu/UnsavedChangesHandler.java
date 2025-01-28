package handlers.menu;

import controllers.GuiController;
import models.FileModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
* TODO: This can likely be refactored to an action but im not sure. Need to re-examine this.
*  Having issues with new tabs that do not have file models yet.
*   Should we not attach this handler if the tab is untitled? Would make it easier -- plus we don't need to track any changes
*   on a new tab that is untitled because ALL changes are usnaved for this file
*/
public class UnsavedChangesHandler implements DocumentListener {

    private JTextArea textArea;
    private GuiController controller;

    public UnsavedChangesHandler(JTextArea textArea, GuiController controller){
        this.textArea = textArea;
        this.controller = controller;
    }

    public GuiController getController(){
        return this.controller;
    }

    public JTextArea getTextArea(){

        return this.textArea;

    }

    private FileModel getModel(){
        int index = this.getController().getGui().getTabbedPane().getSelectedIndex();
        String title = this.getController().getGui().getTabbedPane().getTitleAt(index);
        return this.getController().getFileModelForTab(title);
    }

    public void checkForChanges(){

        String currentContent = this.getTextArea().getText();
        FileModel model = getModel();

        if(model != null){
            boolean hasChanged = !model.getSavedContent().equals(currentContent);
            model.setUnsavedChanges(hasChanged);
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        this.checkForChanges();

    }

    @Override
    public void insertUpdate(DocumentEvent e) {

        this.checkForChanges();

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}

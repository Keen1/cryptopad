package listeners.menu;

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
    private FileModel model;

    public UnsavedChangesHandler(JTextArea textArea, FileModel model){

        this.textArea = textArea;
        this.model = model;

    }

    public FileModel getModel(){

        return this.model;

    }

    public JTextArea getTextArea(){

        return this.textArea;

    }

    public void checkForChanges(){

        String currentContent = this.getTextArea().getText();
        boolean hasChanged = !this.getModel().getSavedContent().equals(currentContent);
        this.getModel().setUnsavedChanges(hasChanged);
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

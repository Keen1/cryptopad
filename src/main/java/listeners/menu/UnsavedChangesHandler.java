package listeners.menu;

import models.NewFileModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public  class UnsavedChangesHandler implements DocumentListener {

    private JTextArea textArea;
    private NewFileModel model;

    public UnsavedChangesHandler(JTextArea textArea, NewFileModel model){

        this.textArea = textArea;
        this.model = model;

    }

    public NewFileModel getModel(){

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

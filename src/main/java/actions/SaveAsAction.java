package actions;

import controllers.GuiController;
import models.FileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class SaveAsAction extends AbstractMenuAction{

    public SaveAsAction(GuiController controller){
        super(controller, "Save As");

    }

    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Save file as");
    }

    @Override
    public void actionPerformed(ActionEvent event){
        //user is either trying to save a new file that has no associated file,
        //OR they are trying to create a copy of an existing file
        //the existing file will have a non-null model associated with it based on the title
        //if there is no model(null) then the user wants to save a new file with a new model
        //in both these instances we will need a new model,
        //if the model is null, we should reset the title of the tab and create a new model with that tab
        //if the model isn't null, we should create a NEW tab with the copy's title...
        String content = this.getController().getSelectedTextAreaContent();
        int index = this.getController().getGui().getTabbedPane().getSelectedIndex();
        String title = this.getController().getGui().getTabbedPane().getTitleAt(index);
        FileModel model = this.getController().getFileModelForTab(title);

        File file = this.getController().getGui().chooseFileToSave();
        if(model == null){
            model = new FileModel(file);

            model.setSavedContent(content);
        }

    }
}

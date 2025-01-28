package actions;

import controllers.GuiController;
import models.FileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class SaveAsAction extends AbstractMenuAction{

    public SaveAsAction(GuiController controller){
        super(controller, "Save As");
        initShortcut();

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
        String newTitle = file.getName();
        //if the model is null this is a completely new file, need to save it and update the tab title
        try{
            if(model == null){
                //init the model and save the content
                model = new FileModel(file);
                String update = model.saveContent(content);
                //update the status label in the view
                this.getController().updateStatus(update);
                //add the new model to the model map given the new title
                this.getController().putFileModelForTab(newTitle, model);
                //set the new title in the view
                this.getController().getGui().setTabTitle(index, newTitle);




                //if the model is NOT null, this is a COPY, a new model needs to be saved and placed given teh selected file's name
                //THEN we need to create a NEW with the copy's title and content.
                //the original model should also be saved
            }else{

                //create the new model
                FileModel newModel = new FileModel(file);
                //update the new model with the content
                String update = model.saveContent(content);
                //send the status update
                this.getController().updateStatus(update);
                //set the new model associated with the new title in the map
                this.getController().putFileModelForTab(newTitle, newModel);
                //add the new(copy) to the view
                this.getController().addNewTabToView(newTitle, content);


                //we also know that the current model exists -- this should be updated as well?
                model.saveContent(content);
                //if this is what we want to do we SHOULD NOT update the status label here as that might
                //be confusing for users




            }
        }catch(IOException e){
            System.out.printf("Error saving content: %s", e.getMessage());

        }


    }
}

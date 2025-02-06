package actions;

import controllers.MainPanelController;
import models.FileModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
/*
* save action - implements the shortcut and general action handler for saving the contents of a tab given its associated
* file model
*/

public class SaveAction extends AbstractMenuAction {

    //constructor
    public SaveAction(MainPanelController controller){
        super(controller, "Save");
        initShortcut();
    }

    //shortcut implementation for the ctrl + S
    @Override
    public void initShortcut(){

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Save the current file");

    }

    //a user will use the shortcut to save the selected editor tab
    //gets the content of the selected tab, its title, and the associated model from the model map
    @Override
    public void actionPerformed(ActionEvent event){

        String content = this.getController().getSelectedTextAreaContent();
        int index = this.getController().getGui().getTabbedPane().getSelectedIndex();
        String title = this.getController().getGui().getTabbedPane().getTitleAt(index);
        FileModel model = this.getController().getFileModelForTab(title);

        //if the model is null not null, the file already exists on disk
        if(model != null){

            if(model.hasUnsavedChanges()){

                try{

                    String status = model.saveContent(content);
                    this.getController().updateStatus(status);

                }catch(IOException e){

                    System.out.printf("Error saving file.\n %s", e.getMessage());

                }
            }
        //if the model is null then no model exists in the map for the file as the file does not exist on disk
        }else{

            File file = this.getController().getGui().chooseFileToSave();

            if(file != null){

                model = new FileModel(file);
                String newTitle = file.getName();
                this.getController().getGui().setTabTitle(index, newTitle);
                this.getController().putFileModelForTab(newTitle, model);

                try{

                    String status = model.saveContent(content);
                    this.getController().updateStatus(status);
                    this.getController().getGui()
                            .attachUnsavedChangesHandler(this.getController().getGui().getTextAreaForSelectedTab());

                }catch(IOException e){

                    System.out.printf("Error saving file.\n%s", e.getMessage());

                }
            }
        }
    }
}

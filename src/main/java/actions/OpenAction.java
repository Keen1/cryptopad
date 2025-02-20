package actions;

import controllers.MainPanelController;
import models.FileModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;

/*
* open action - implements the shortcut and general action handler for opening a file in an editor tab
*/
public class OpenAction extends AbstractMenuAction {

    public OpenAction(MainPanelController controller){
        super(controller, "Open");
        initShortcut();
    }

    //shortcut implementation for ctrl + O
    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Open a file");
    }

    //get the file to open in the editor tab, create a model for it and add the content to the view
    @Override
    public void actionPerformed(ActionEvent event){

        //get a file the user chooses
        File file = this.getController().getGui().chooseFileToOpen();

        //ensure it is readable
        if(this.isFileReadable(file)){

            //instantiate a model for the file
            FileModel model = new FileModel(file);

            //if a key exists for this file it has been encrypted, so we need to read and set our parameters from the
            //header
            try{
                if(this.getController().getGui().getKeyController().hasKeyForAlias(file.getName())){
                    
                }
            }catch(KeyStoreException e){
                System.out.printf("error accessing keystore: %s\n", e.getMessage());
            }


            //instantiate the model, add the model to the model map, then add the content of the file to a editor tab
            try{
                model.initState();
                this.getController().putFileModelForTab(file.getName(), model);
                this.getController().addNewTabToView(file.getName(), model.getSavedContent());
            }catch(IOException e){
                System.out.printf("Error initializing file model: %s", e.getMessage());
            }
        }
    }
}

package actions;

import controllers.GuiController;
import models.FileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class OpenAction extends AbstractMenuAction {

    public OpenAction(GuiController controller){
        super(controller, "Open");
        initShortcut();
    }

    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Open a file");
    }

    @Override
    public void actionPerformed(ActionEvent event){
        File file = this.getController().getGui().chooseFileToOpen();
        if(this.isFileReadable(file)){
            FileModel model = new FileModel(file);
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

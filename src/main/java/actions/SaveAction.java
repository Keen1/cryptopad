package actions;

import controllers.GuiController;
import models.FileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class SaveAction extends AbstractMenuAction {

    public SaveAction(GuiController controller){
        super(controller, "Save");
        initShortcut();
    }

    @Override
    public void initShortcut(){

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Save the current file");

    }

    @Override
    public void actionPerformed(ActionEvent event){

        String content = this.getController().getSelectedTextAreaContent();
        int index = this.getController().getGui().getTabbedPane().getSelectedIndex();
        String title = this.getController().getGui().getTabbedPane().getTitleAt(index);
        FileModel model = this.getController().getFileModelForTab(title);

        if(model != null){
            if(model.hasUnsavedChanges()){
                try{
                    String status = model.saveContent(content);
                    this.getController().updateStatus(status);
                }catch(IOException e){
                    System.out.printf("Error saving file.\n %s", e.getMessage());
                }
            }


        }else{

            /*
            TODO This tab's text area needs to have an unsaved changes handler attached to it
             The tab was untitled and the unsaved changes handler is only attached if the title does not equal "untitled"
             */

            File file = this.getController().getGui().chooseFileToSave();

            if(file != null){

                model = new FileModel(file);
                String newTitle = file.getName();
                this.getController().getGui().setTabTitle(index, newTitle);
                this.getController().putFileModelForTab(newTitle, model);

                try{

                    String status = model.saveContent(content);
                    this.getController().updateStatus(status);

                }catch(IOException e){

                    System.out.printf("Error saving file.\n%s", e.getMessage());

                }
            }
        }
    }
}

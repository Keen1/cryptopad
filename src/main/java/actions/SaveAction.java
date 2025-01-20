package actions;

import controllers.GuiController;
import models.FileModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

//TODO if the file hasn't had any changes made to it(if savedContent == textArea.getText()) we shouldn't run this action

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
        try{
            String status = model.saveContent(content);
            this.getController().updateStatus(status);
        }catch(IOException e){
            System.out.printf("Error saving file.\n %s", e.getMessage());
        }
    }
}

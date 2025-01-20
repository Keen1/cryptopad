package actions;

import controllers.GuiController;

import javax.swing.*;
import java.io.File;

public abstract class AbstractMenuAction extends AbstractAction {

    private final GuiController controller;

    public AbstractMenuAction(GuiController controller, String name){

        super(name);
        this.controller = controller;
    }

    public GuiController getController(){
        return this.controller;
    }

    public boolean isFileReadable(File file){
        if(file != null){
            return file.exists() && file.isFile() && file.canRead();
        }
        return false;
    }
    //can be concrete, just need to parameterize the shortcut and the description
    public abstract void initShortcut();

}

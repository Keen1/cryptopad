package actions;

import controllers.GuiController;
import javax.swing.*;
import java.io.File;
/*
* Abstract menu action -- any menu action implemented must derive from this class
*
*/
public abstract class AbstractMenuAction extends AbstractAction {
    //all actions require the controller
    private final GuiController controller;

    //all derived actions must implement their own shortcut
    public abstract void initShortcut();

    //constructor
    public AbstractMenuAction(GuiController controller, String name){

        super(name);
        this.controller = controller;
    }

    //controller getter
    public GuiController getController(){
        return this.controller;
    }

    //check if a file is readable. Some actions associated with the menu require file handling
    public boolean isFileReadable(File file){
        if(file != null){
            return file.exists() && file.isFile() && file.canRead();
        }
        return false;
    }


}

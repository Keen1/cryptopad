package actions;

import controllers.MainPanelController;
import javax.swing.*;
import java.io.File;
/*
* Abstract menu action -- any menu action implemented must derive from this class
*
*/
public abstract class AbstractMenuAction extends AbstractAction {
    //all actions require the controller
    private final MainPanelController controller;

    //all derived actions must implement their own shortcut
    public abstract void initShortcut();

    //constructor
    public AbstractMenuAction(MainPanelController controller, String name){

        super(name);
        this.controller = controller;
    }

    //controller getter
    public MainPanelController getController(){
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

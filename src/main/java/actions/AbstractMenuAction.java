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
    //should it b concrete? Everything we need is defined and known at compile time not at runtime. None of these actions
    //dynamically change to require this? not sure.
    public abstract void initShortcut();

}

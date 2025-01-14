package listeners.menu;

import controllers.GuiController;
import models.NewFileModel;

import java.io.File;

public abstract class AbstractMenuItemHandler implements MenuItemListener{
    private final GuiController controller;

    public AbstractMenuItemHandler(GuiController controller){
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

}

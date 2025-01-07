package listeners;

import controllers.GuiController;
import models.FileModel;

import java.io.File;

public abstract class AbstractMenuItemListener implements MenuItemListener{
    private final FileModel model;
    private final GuiController controller;

    public AbstractMenuItemListener(FileModel model, GuiController controller){
        this.model = model;
        this.controller = controller;
    }
    public GuiController getController(){
        return this.controller;
    }

    public FileModel getModel(){
        return this.model;
    }

    public boolean isFileReadable(File file){
        if(file != null){

            return file.exists() && file.isFile() && file.canRead();

        }

        return false;
    }

}

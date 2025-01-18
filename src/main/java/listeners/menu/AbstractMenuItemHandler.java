package listeners.menu;

import controllers.GuiController;

import java.io.File;
/*
* TODO: This is going to eventually be deprecated and deleted once we move our functionality to Actions.
*  We will still need to utility of testing the readability of the file. Should probably move this function to the
*  model.
*/
public abstract class AbstractMenuItemHandler implements MenuItemHandler {
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

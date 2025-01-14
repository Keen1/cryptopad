package listeners.menu;

import controllers.GuiController;
import models.NewFileModel;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class OpenItemHandler extends AbstractMenuItemHandler{

    public OpenItemHandler(GuiController controller){
        super(controller);
    }

    public void actionPerformed(ActionEvent event) {
        File file = this.getController().getGui().chooseFileToOpen();

        if(this.isFileReadable(file)){
            System.out.printf("file selected : %s", file.getName());
            NewFileModel model = new NewFileModel(file);

            try{
                model.initState();
            }catch(IOException e){
                System.out.printf("error initializing file model: %s", e.getMessage());
            }
            finally{
                this.getController().putFileModelForTab(file.getName(), model);
                this.getController().addNewTabToView(file.getName(), model.getSavedContent());
            }

        }
    }




}

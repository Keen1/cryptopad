package listeners;

import models.FileModel;

import java.awt.event.ActionEvent;
import java.io.File;

public class OpenItemListener extends AbstractMenuItemListener {

    public OpenItemListener(FileModel model){
        super(model);
    }

    public void actionPerformed(ActionEvent event) {

        File file = this.getModel().chooseFileToOpen();
        if(file != null){
            if(this.isFileReadable(file)){
                System.out.printf("file selected : %s", file.getName());
            }else{
                System.out.printf("invalid file selected: %s", file.getName());
            }
        }else{
            System.out.println("User cancelled.");
        }


    }
}

package listeners;

import models.FileModel;

import java.io.File;

public abstract class AbstractMenuItemListener implements MenuItemListener{
    private final FileModel model;

    public AbstractMenuItemListener(FileModel model){
        this.model = model;
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

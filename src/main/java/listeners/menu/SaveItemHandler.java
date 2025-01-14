package listeners.menu;

import controllers.GuiController;
import models.NewFileModel;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class SaveItemHandler extends AbstractMenuItemHandler{
    public SaveItemHandler(GuiController controller){
        super(controller);
    }

    @Override
    public void actionPerformed(ActionEvent event){
        String content = this.getController().getSelectedTextAreaContent();
        int index = this.getController().getGui().getTabbedPane().getSelectedIndex();
        String title = this.getController().getGui().getTabbedPane().getTitleAt(index);
        NewFileModel model = this.getController().getFileModelForTab(title);
        try{
            model.saveContent(content);

        }catch(IOException e){
            System.out.printf("error saving content: %s", e.getMessage());
        }

    }

}

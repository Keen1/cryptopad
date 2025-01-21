package actions;

import controllers.GuiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NewAction extends AbstractMenuAction {

    public NewAction(GuiController controller){
        super(controller, "new");
        initShortcut();
    }




    //having problems with a model for this -- the model is going to need to be refactored to account for new usnaved tabs
    //currently the unsaved tab is added to the view but a model doesn't exist for it yet.
    //NOTE: In the OpenAction, we instantiate the model first -- FileModel model = new FileModel(file), then model.initState()
    //here we don't have a file to pass -- we only have a title and an empty string content.
    //also this will cause problems in our map if we have more than one unsaved, unnamed model -- since the title is used
    //as the key these models will not co-exist in the map
    @Override
    public void actionPerformed(ActionEvent event){
        this.getController().addNewTabToView("untitled", "");
    }

    @Override
    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "open a new file");
    }
}

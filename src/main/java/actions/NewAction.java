package actions;

import controllers.MainPanelController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
/*
* New action - implements the shortcut and general action handler for creating a new file in an editor tab
* */
public class NewAction extends AbstractMenuAction {

    public NewAction(MainPanelController controller){
        super(controller, "new");
        initShortcut();
    }



    //simply adds an untitled tab to the tabbedPane
    //does NOT require a model -- there is no persistence associated with this tab YET
    @Override
    public void actionPerformed(ActionEvent event){
        this.getController().addNewTabToView("untitled", "");
    }

    //shortcut implementation for ctrl + N
    @Override
    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "open a new file");
    }
}

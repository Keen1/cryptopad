package actions;

import controllers.GuiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SaveAsAction extends AbstractMenuAction{

    public SaveAsAction(GuiController controller){
        super(controller, "Save As");

    }

    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Save file as");
    }

    @Override
    public void actionPerformed(ActionEvent event){

    }
}

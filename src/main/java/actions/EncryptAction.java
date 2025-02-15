package actions;

import controllers.MainPanelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class EncryptAction extends AbstractMenuAction{
    public EncryptAction(MainPanelController controller){
        super(controller, "encrypt");
        initShortcut();

    }

    @Override
    public void actionPerformed(ActionEvent event){
    }

    @Override
    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.SHIFT_DOWN_MASK
                | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        putValue(SHORT_DESCRIPTION, "encrypt the current file");
    }
}

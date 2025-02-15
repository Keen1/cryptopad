package actions;

import controllers.MainPanelController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DecryptAction extends AbstractMenuAction{

    public DecryptAction(MainPanelController controller){
        super(controller, "decrypt");
        initShortcut();

    }

    @Override
    public void actionPerformed(ActionEvent event){

    }

    @Override
    public void initShortcut(){
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.SHIFT_DOWN_MASK
        | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }
}

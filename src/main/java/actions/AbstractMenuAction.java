package actions;

import controllers.GuiController;

import javax.swing.*;

public abstract class AbstractMenuAction extends AbstractAction {

    private GuiController controller;
    private String name;

    public AbstractMenuAction(GuiController controller, String name){

        super(name);
        this.controller = controller;
    }

    public GuiController getController(){
        return this.controller;
    }
    //can be concrete, just need to parameterize the shortcut and the description
    public abstract void initShortcut();

}

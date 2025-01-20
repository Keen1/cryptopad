package actions;

import controllers.GuiController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewAction extends AbstractAction {
    private GuiController controller;

    public NewAction(GuiController controller){
        super("new");
        this.controller = controller;
    }

    public GuiController getController(){
        return this.controller;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        this.getController().addNewTabToView("untitled", "");
    }
}

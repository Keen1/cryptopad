package actions;

import controllers.GuiController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class NewAction extends AbstractMenuAction {
    private GuiController controller;

    public NewAction(GuiController controller){
        super(controller, "new");
    }

    public GuiController getController(){
        return this.controller;
    }



    @Override
    public void actionPerformed(ActionEvent event){
        this.getController().addNewTabToView("untitled", "");
    }
    @Override
    public void initShortcut(){

    }
}

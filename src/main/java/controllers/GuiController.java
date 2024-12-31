package controllers;

import gui.Gui;

public class GuiController {
    private Gui gui;
    public GuiController(Gui gui){
        this.gui = gui;
    }


    public Gui getGui(){
        return this.gui;
    }

}

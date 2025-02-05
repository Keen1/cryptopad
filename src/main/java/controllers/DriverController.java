package controllers;

import gui.GuiDriver;

public class DriverController {
    private GuiDriver driver;
    public DriverController(GuiDriver driver){
        this.driver = driver;
    }
    public GuiDriver getDriver(){
        return this.driver;
    }


}

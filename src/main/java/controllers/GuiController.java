package controllers;

import gui.Gui;
import listeners.OpenItemListener;
import models.FileModel;

import javax.swing.*;

public class GuiController {
    private Gui gui;
    private FileModel model;

    public GuiController(Gui gui, FileModel model){
        this.gui = gui;
        this.model = model;
        registerMenuListeners();
    }

    public FileModel getModel() {
        return model;
    }

    public Gui getGui(){
        return this.gui;
    }

    public void registerMenuListeners(){
        JMenuItem openItem = this.getGui().getOpenItem();
        openItem.addActionListener(new OpenItemListener(this.getModel(), this));
    }

    public void addNewTabToView(String name, String content){
        this.getGui().addNewTab(name, content);
    }
}

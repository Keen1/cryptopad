package controllers;

import gui.Gui;
import listeners.menu.OpenItemHandler;
import listeners.menu.OpenItemListener;
import listeners.menu.SaveItemHandler;
import models.FileModel;
import models.NewFileModel;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class GuiController {
    private Gui gui;
    private FileModel model;
    private HashMap<String, NewFileModel> fileTabMap;


    public GuiController(Gui gui, FileModel model){
        this.gui = gui;
        this.model = model;
        registerMenuListeners();
        initMap();
    }
    public GuiController(HashMap<String, NewFileModel> fileTabMap, Gui gui){
        this.fileTabMap = fileTabMap;
        this.gui = gui;
    }

    public HashMap<String, NewFileModel> getFileTabMap() {
        return fileTabMap;
    }
    public void setFileTabMap(HashMap<String, NewFileModel> fileTabMap){
        this.fileTabMap = fileTabMap;
    }
    public NewFileModel getFileModelForTab(String title){
        return this.fileTabMap.get(title);
    }

    public void putFileModelForTab(String title, NewFileModel model){
        this.fileTabMap.put(title, model);
    }
    public void removeModelForTab(String title){
        this.fileTabMap.remove(title);
    }

    public FileModel getModel() {
        return model;
    }

    public Gui getGui(){
        return this.gui;
    }
    private void initMap(){
        this.fileTabMap = new HashMap<>();
    }

    public void saveTabContent(String title)throws IOException {
        NewFileModel model = this.getFileModelForTab(title);
        String newContent = getContentForTab(title);
        model.saveContent(newContent);

    }

    public String getContentForTab(String title){
        return this.getGui().getTabContent(title);
    }

    public String getSelectedTextAreaContent(){
        return this.getGui().getTextAreaForSelectedTab().getText();
    }

    public void registerMenuListeners(){
        JMenuItem openItem = this.getGui().getOpenItem();
        openItem.addActionListener(new OpenItemHandler(this));
        JMenuItem saveItem = this.getGui().getSaveItem();
        saveItem.addActionListener(new SaveItemHandler(this));


    }

    public void addNewTabToView(String name, String content){
        this.getGui().addNewTab(name, content);
    }
}

package controllers;

import gui.Gui;
import listeners.menu.OpenItemHandler;
import models.FileModel;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

/*
* Controller class for orchestration between gui and model/handlers
*/
public class GuiController {

    private Gui gui;
    private HashMap<String, FileModel> fileTabMap;

    //constructor
    public GuiController(Gui gui){
        this.gui = gui;
        registerMenuListeners();
        initMap();
    }

    //hashmap constructor
    public GuiController(HashMap<String, FileModel> fileTabMap, Gui gui){

        this.fileTabMap = fileTabMap;
        this.gui = gui;

    }

    //getter for model map
    public HashMap<String, FileModel> getFileTabMap() {
        return fileTabMap;
    }

    //setter for model map
    public void setFileTabMap(HashMap<String, FileModel> fileTabMap){
        this.fileTabMap = fileTabMap;
    }

    //get a file model given the title of the tab(file name)
    public FileModel getFileModelForTab(String title){
        return this.fileTabMap.get(title);
    }

    //put a file model and title
    public void putFileModelForTab(String title, FileModel model){
        this.fileTabMap.put(title, model);
    }

    //remove a file model given the title
    public void removeModelForTab(String title){
        this.fileTabMap.remove(title);
    }

    //refactor

    //getter for gui
    public Gui getGui(){
        return this.gui;
    }

    //init the model map
    private void initMap(){
        this.fileTabMap = new HashMap<>();
    }

    //save the content of a tab given its title
    public void saveTabContent(String title)throws IOException {

        FileModel model = this.getFileModelForTab(title);
        String newContent = getContentForTab(title);
        String status = model.saveContent(newContent);
        this.updateStatus(status);




    }


    public void updateStatus(String update){
        this.getGui().updateStatus(update);
    }

    //get the content of a tab given the title
    public String getContentForTab(String title){
        return this.getGui().getTabContent(title);
    }

    //get the currently selected tab's content
    public String getSelectedTextAreaContent(){
        return this.getGui().getTextAreaForSelectedTab().getText();
    }

    //register handlers for the main menu
    public void registerMenuListeners(){

        //JMenuItem openItem = this.getGui().getOpenItem();
        //openItem.addActionListener(new OpenItemHandler(this));
        //JMenuItem saveItem = this.getGui().getSaveItem();
        //saveItem.addActionListener(new SaveItemHandler(this));

    }

    //add a new tab to the gui given the title and its content
    public void addNewTabToView(String name, String content){
        this.getGui().addNewTab(name, content);
    }
}

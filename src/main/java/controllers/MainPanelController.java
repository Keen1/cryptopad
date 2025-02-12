package controllers;

import components.MainPanel;
import models.FileModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* Controller class for orchestration between gui and model/handlers
*/
public class MainPanelController {

    private MainPanel mainPanel;
    private HashMap<String, FileModel> fileTabMap;

    //constructor
    public MainPanelController(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        initMap();
    }

    //hashmap constructor
    public MainPanelController(HashMap<String, FileModel> fileTabMap, MainPanel mainPanel){

        this.fileTabMap = fileTabMap;
        this.mainPanel = mainPanel;

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
    public MainPanel getGui(){
        return this.mainPanel;
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

    //add a new tab to the gui given the title and its content
    public void addNewTabToView(String name, String content){
        this.getGui().addNewTab(name, content);
    }

    public List<String> getTitles(){
        List<String> titles = new ArrayList<>();
        for(int i = 0; i < this.getGui().getTabbedPane().getTabCount(); i++){
            titles.add(this.getGui().getTabbedPane().getTitleAt(i));
        }
        return titles;
    }
}

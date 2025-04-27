package controllers;

import components.dialogs.PreferencesDialog;
import models.PreferencesModel;
import util.constants.AppConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


/*
* Preferences controller -- manages the model and dialog for preferences selection.
*/
public class PreferencesController {
    //model instance
    private PreferencesModel model;
    //dialog instance
    private PreferencesDialog prefDialog;
    //look and feel map instance
    private HashMap<String, String> lafMap;

    //constructor
    public PreferencesController(PreferencesDialog prefDialog){
        this.prefDialog = prefDialog;
        initModel();
        initPreferences();
    }

    //default
    public PreferencesController(){
        initModel();
        initPreferences();
    }

    //getter for the dialog
    public PreferencesDialog getPreferencesDialog(){
        return this.prefDialog;
    }

    //setter for the preferences dialog
    public void setPreferencesDialog(PreferencesDialog prefDialog){
        this.prefDialog = prefDialog;
    }

    //getter for the laf map
    public HashMap<String, String> getLafMap(){
        return this.lafMap;
    }

    //get the value(full class name) of a look and feel given its name
    public String getLafClassName(String name){
        return this.getLafMap().get(name);
    }

    //setter for the look and feel map
    public void setLafMap(HashMap<String, String> lafMap){
        this.lafMap = lafMap;
    }

    //setter for the model
    public void setModel(PreferencesModel model){
        this.model = model;
    }

    //getter for the model
    public PreferencesModel getModel(){
        return this.model;
    }

    //init function for the model
    public void initModel(){
        this.model = PreferencesModel.getInstance();
    }

    //init function for default preferences
    public void initDefaults(){

        try{
            this.getModel().setDefaults();
            this.getModel().writePreferences();

        }catch(IOException e){
            System.out.printf("error accessing preferences file: %s", e.getMessage());
        }
    }

    //init function for preferences read from the config file
    public void initPreferences(){

        Path preferencesPath = Paths.get(AppConstants.PREFERENCES_PATH);

        try{
            if(Files.exists(preferencesPath)){
                this.getModel().readPreferences();
            }else{
                this.getModel().setDefaults();
            }
        }catch(IOException e){
            System.out.printf("error reading preferences file: %s\n", e.getMessage());
        }
    }

    //save preferences given their values
    public void savePreferences(String theme, String fontFamily, int fontSize){

        this.getModel().setPreferences(theme, fontFamily, fontSize);

        try{

            this.getModel().writePreferences();

        }catch(IOException e){
            System.out.printf("error saving preferences: %s\n", e.getMessage());
        }
    }

    //getter for the current theme preference
    public String getTheme(){
        return this.getModel().getTheme();
    }

    //setter for the current theme
    public void setTheme(String theme){
        this.getModel().setTheme(theme);
    }

    //getter for the current font family
    public String getFontFamily(){
        return this.getModel().getFontFamily();
    }

    //setter for the current font family
    public void setFontFamily(String fontFamily){
        this.getModel().setFontFamily(fontFamily);
    }

    //getter for the current font size
    public int getFontSize(){
        return this.getModel().getFontSize();
    }

    //setter for the current font size
    public void setFontSize(int fontSize){
        this.getModel().setFontSize(fontSize);
    }

    //TODO - delete? really should only be used for troubleshooting I think.
    public void showPreferences(){
        System.out.printf("preferences state: %s", this.getModel().toString());
    }






}

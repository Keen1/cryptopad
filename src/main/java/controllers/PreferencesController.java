package controllers;

import models.PreferencesModel;
import util.constants.AppConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreferencesController {

    private PreferencesModel model;

    public PreferencesController(){
        initModel();
        initPreferences();
    }

    public void setModel(PreferencesModel model){
        this.model = model;
    }
    public PreferencesModel getModel(){
        return this.model;
    }

    public void initModel(){
        this.model = PreferencesModel.getInstance();
    }

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
    
    public void savePreferences(String theme, String fontFamily, int fontSize){

        this.getModel().setPreferences(theme, fontFamily, fontSize);

        try{

            this.getModel().writePreferences();

        }catch(IOException e){
            System.out.printf("error saving preferences: %s\n", e.getMessage());
        }
    }


    public String getTheme(){
        return this.getModel().getTheme();
    }
    public void setTheme(String theme){
        this.getModel().setTheme(theme);
    }
    public String getFontFamily(){
        return this.getModel().getFontFamily();
    }
    public void setFontFamily(String fontFamily){
        this.getModel().setFontFamily(fontFamily);
    }
    public int getFontSize(){
        return this.getModel().getFontSize();
    }
    public void setFontSize(int fontSize){
        this.getModel().setFontSize(fontSize);
    }






}

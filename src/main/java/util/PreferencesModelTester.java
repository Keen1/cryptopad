package util;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.PreferencesModel;
import util.constants.AppConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreferencesModelTester {
    private PreferencesModel prefModel;
    public PreferencesModelTester(){

    }
    public PreferencesModelTester(PreferencesModel prefModel){
        this.prefModel = prefModel;
    }

    public void setPreferencesModel(PreferencesModel prefModel){
        this.prefModel = prefModel;
    }

    public PreferencesModel getPreferencesModel(){
        return this.prefModel;
    }

    public String getTheme(){
        return this.getPreferencesModel().getTheme();

    }
    public void setTheme(String theme){
        this.getPreferencesModel().setTheme(theme);
    }
    public String getFontFamily(){
        return this.getPreferencesModel().getFontFamily();
    }
    public void setFontFamily(String fontFamily){
        this.getPreferencesModel().setFontFamily(fontFamily);
    }
    public int getFontSize(){
        return this.getPreferencesModel().getFontSize();
    }
    public void setFontSize(int fontSize){
        this.getPreferencesModel().setFontSize(fontSize);
    }








}

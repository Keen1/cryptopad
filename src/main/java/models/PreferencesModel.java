package models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import util.constants.AppConstants;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreferencesModel {
    private String theme;
    private String fontFamily;
    private int fontSize;
    private static PreferencesModel instance;

    private PreferencesModel(){

    }

    public static PreferencesModel getInstance(){
        if(instance == null){
            instance = new PreferencesModel();
        }
        return instance;
    }

    public void setDefaults() throws IOException {


        String theme = UIManager.getSystemLookAndFeelClassName();
        String fontFamily = UIManager.getFont("TextArea.font").getFamily();
        int fontSize = UIManager.getFont("TextArea.font").getSize();

        this.setPreferences(theme, fontFamily, fontSize);

    }

    public void setPreferences(String theme, String fontFamily, int fontSize){
        this.setTheme(theme);
        this.setFontFamily(fontFamily);
        this.setFontSize(fontSize);
    }

    public void setTheme(String theme){
        this.theme = theme;
    }
    public String getTheme(){
        return this.theme;
    }

    public void setFontFamily(String fontFamily){
        this.fontFamily = fontFamily;
    }
    public String getFontFamily(){
        return this.fontFamily;
    }

    public void setFontSize(int fontSize){
        this.fontSize = fontSize;
    }
    public int getFontSize(){
        return this.fontSize;
    }

    public void readPreferences() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        byte[] data = Files.readAllBytes(Paths.get(AppConstants.PREFERENCES_PATH));
        instance = mapper.readValue(data, PreferencesModel.class);
    }

    public void writePreferences() throws IOException {
        ObjectMapper mapper  = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(AppConstants.PREFERENCES_PATH), this);
    }

    @Override
    public String toString(){
        return String.format("theme: %s\n font family: %s\n font size: %d\n", this.getTheme(), this.getFontFamily(), this.getFontSize());

    }

}

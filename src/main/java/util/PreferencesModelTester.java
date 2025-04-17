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







}

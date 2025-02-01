package controllers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class ConfigController {
    public class Config{
        private String keyStoreDir;
        private String lookAndFeel;
        private boolean firstRun;

        public String getKeyStoreDir(){
            return this.keyStoreDir;
        }
        public void setKeyStoreDir(String keyStoreDir){
            this.keyStoreDir = keyStoreDir;
        }
        public String getLookAndFeel(){
            return this.lookAndFeel;
        }
        public void setLookAndFeel(String lookAndFeel){
            this.lookAndFeel = lookAndFeel;
        }
        public boolean isFirstRun(){
            return this.firstRun;
        }
        public void setFirstRun(boolean firstRun){
            this.firstRun = firstRun;
        }
    }
    private ObjectMapper mapper;
    private Config currentConfig;
    private static final String CONFIG_PATH = "/config.json";

    public ConfigController(){
        this.mapper = new ObjectMapper();
        loadDefaultConfig();

    }
    private void loadDefaultConfig(){
        try(InputStream inStream  = getClass().getResourceAsStream(CONFIG_PATH)){
            this.currentConfig = mapper.readValue(inStream, Config.class);
        }catch(IOException e){
            System.out.printf("Failed to load default config: %s", e.getMessage());
        }
    }


}

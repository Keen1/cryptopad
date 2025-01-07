package models;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileModel {
    public FileModel(){

    }

    public void SaveFile(String content, File file){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            writer.write(content);
        } catch (IOException e) {
            System.out.printf("error: %s", e.getMessage());
        }
    }

    public File chooseFileToOpen(){
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }



}

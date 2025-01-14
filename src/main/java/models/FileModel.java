package models;

import javax.swing.*;
import java.io.*;

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

    public String getFileContent(File file){
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;

            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");

            }

        } catch (IOException e) {
            System.out.printf("error reading file contents: %s", e.getMessage());
        }
        return sb.toString();
    }



}

package models;

import java.io.*;
import java.nio.file.Files;

/*
* File model class. All file work handled here.
* How to refactor this to account for files that do not exist on disk yet?
* currently we are throwing exceptions in the reading of the file doesn't work but the file reading will never work
* for untitled, unsaved new documents -- they don't exist on disk yet.
* can just use a flag maybe? boolean onDisk = false; ??
*/

public class FileModel {
    private int index;
    private String savedContent;
    private boolean unsavedChanges;
    private File file;
    private String cipherAlgorithm;
    private String blockMode;
    private String paddingScheme;


    public FileModel(File file){
        this.file = file;
        this.unsavedChanges = false;
    }
    public FileModel(int index){
        this.index = index;
        this.unsavedChanges = false;
    }

    public void initState() throws IOException{
        this.setSavedContent(this.getFileContent());


    }
    public String getFileContent() throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
        String line;
        while((line = reader.readLine()) != null){
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public void setFile(File file){
        this.file = file;
    }

    public File getFile(){
        return this.file;
    }

    public void setUnsavedChanges(boolean unsavedChanges){

        if(this.unsavedChanges != unsavedChanges){

            this.unsavedChanges = unsavedChanges;
        }
    }

    public boolean hasUnsavedChanges(){
        return this.unsavedChanges;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

    public void setSavedContent(String savedContent){
        this.savedContent = savedContent;
    }

    public String getSavedContent(){
        return this.savedContent;
    }

    public String saveContent(String content) throws IOException {

        File backup = new File(this.getFile().getPath() + ".bak");
        if(this.getFile().exists()){

            Files.copy(this.getFile().toPath(), backup.toPath());

        }

        try{

            BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            writer.write(content);
            writer.flush();
            this.setSavedContent(content);
            this.setUnsavedChanges(false);
            boolean deleted = backup.delete();
            System.out.printf("Save content success operation: %b\n", deleted);
            return "Saved " + this.getFile().getName();


        }catch(IOException e){

            if(backup.exists()){
                Files.copy(backup.toPath(), this.getFile().toPath());
                System.out.printf("error saving file, backup restored: %s\n", e.getMessage());

            }
            return "Failed saving " + this.getFile().getName();

        }



    }

    public void setCipherAlgorithm(String cipherAlgorithm){
        this.cipherAlgorithm = cipherAlgorithm;
    }
    public String getCipherAlgorithm(){
        return this.cipherAlgorithm;
    }
    public void setBlockMode(String blockMode){
        this.blockMode = blockMode;
    }
    public String getBlockMode(){
        return this.blockMode;
    }
    public void setPaddingScheme(String paddingScheme){
        this.paddingScheme = paddingScheme;
    }
    public String getPaddingScheme(){
        return this.paddingScheme;
    }
}

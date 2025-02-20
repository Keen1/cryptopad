package models;

import util.enums.CipherBlockSizes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

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
    private String decryptedContent;
    private boolean unsavedChanges;
    private File file;

    private boolean isEncrypted;

    private String cipherAlgorithm;
    private String blockMode;
    private String padding;



    public FileModel(File file){
        this.file = file;
        this.unsavedChanges = false;
    }
    public FileModel(int index){
        this.index = index;
        this.unsavedChanges = false;
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
    public void setPadding(String padding){
        this.padding = padding;
    }
    public String getPadding(){
        return this.padding;
    }
    public int getIvSize(){
        return CipherBlockSizes.fromString(this.getCipherAlgorithm()).getIvSize();
    }

    public void setDecryptedContent(String decryptedContent){
        this.decryptedContent = decryptedContent;
    }
    public String getDecryptedContent(){
        return this.decryptedContent;
    }




    private boolean isEncrypted(){
        return this.isEncrypted;
    }
    public void setEncrypted(boolean isEncrypted){
        this.isEncrypted = isEncrypted;
    }

    public String getTransformation(){
        return String.format("%s/%s/%s", this.getCipherAlgorithm(), this.getBlockMode(), this.getPadding());
    }


    public void encrypt(SecretKey key, IvParameterSpec iv) throws Exception{
        if(!this.isEncrypted()){
            String content = this.getSavedContent();
            String transformation = this.getTransformation();
            Cipher cipher = Cipher.getInstance(transformation, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptedBytes = cipher.doFinal(content.getBytes());
            byte[] combinedBytes = new byte[iv.getIV().length + encryptedBytes.length];
            System.arraycopy(iv.getIV(), 0, combinedBytes, 0, iv.getIV().length);
            System.arraycopy(encryptedBytes, 0, combinedBytes, iv.getIV().length, encryptedBytes.length);
            String encryptedContent = Base64.getEncoder().encodeToString(combinedBytes);
            this.saveContent(encryptedContent);
            this.setEncrypted(true);
        }

    }

    public void encrypt2(SecretKey key, IvParameterSpec iv) throws Exception{

        if(!this.isEncrypted()){

            this.setDecryptedContent(this.getSavedContent());
            String transformation = this.getTransformation();

            Cipher cipher = Cipher.getInstance(transformation, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] encryptedBytes = cipher.doFinal(this.getDecryptedContent().getBytes());
            byte[] combinedBytes = new byte[iv.getIV().length + encryptedBytes.length];

            System.arraycopy(iv.getIV(), 0, combinedBytes, 0, iv.getIV().length);
            System.arraycopy(encryptedBytes, 0, combinedBytes, iv.getIV().length, encryptedBytes.length);

            String encryptedContent = Base64.getEncoder().encodeToString(combinedBytes);
            this.saveContent(encryptedContent);
            this.setEncrypted(true);

        }
    }

    public void decrypt2(SecretKey key) throws Exception{

        if(this.isEncrypted()){
            byte[] combinedBytes = Base64.getDecoder().decode(this.getSavedContent());
            String transformation = this.getTransformation();

            int ivSize = this.getIvSize();

            byte[] iv = new byte[ivSize];
            System.arraycopy(combinedBytes, 0, iv, 0, ivSize);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            byte[] encryptedContent = new byte[combinedBytes.length - ivSize];
            System.arraycopy(combinedBytes, ivSize, encryptedContent, 0, encryptedContent.length);

            Cipher cipher = Cipher.getInstance(transformation, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedContent);
            this.setDecryptedContent(new String(decryptedBytes));
            this.setEncrypted(false);
        }
    }

    public void decrypt(SecretKey key) throws Exception{
        if(this.isEncrypted()){
            byte[] combinedBytes = Base64.getDecoder().decode(this.getSavedContent());
            String transformation = this.getTransformation();

            int ivSize = this.getIvSize();
            byte[] iv = new byte[ivSize];
            System.arraycopy(combinedBytes, 0, iv, 0, ivSize);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            byte[] encryptedContent = new byte[combinedBytes.length - ivSize];
            System.arraycopy(combinedBytes, ivSize, encryptedContent, 0, encryptedContent.length);

            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedContent);

            this.setSavedContent(new String(decryptedBytes));
            this.setEncrypted(false);

        }
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

}

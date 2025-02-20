package models;

import util.enums.CipherBlockSizes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.ByteBuffer;
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
    private boolean unsavedChanges;
    private File file;


    private String cipherAlgorithm;
    private String blockMode;
    private String padding;
    private IvParameterSpec iv;



    public FileModel(File file){
        this.file = file;
        this.unsavedChanges = false;
    }
    public FileModel(int index){
        this.index = index;
        this.unsavedChanges = false;
    }

    public void setIV(IvParameterSpec iv){
        this.iv = iv;
    }

    public IvParameterSpec getIV(){
        return this.iv;
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

    public String getTransformation(){
        return String.format("%s/%s/%s", this.getCipherAlgorithm(), this.getBlockMode(), this.getPadding());
    }




    public String encryptContent(SecretKey key) throws Exception{
        String content = this.getSavedContent();
        String transformation = this.getTransformation();
        byte[] transformBytes = transformation.getBytes();
        byte[] transformLength = ByteBuffer.allocate(4).putInt(transformBytes.length).array();


        Cipher cipher = Cipher.getInstance(transformation, "BC");
        IvParameterSpec iv = this.getIV();
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] encryptedBytes = cipher.doFinal(content.getBytes());
        byte[] combinedBytes = new byte[4 + transformBytes.length + iv.getIV().length + encryptedBytes.length];

        int offset = 0;
        System.arraycopy(transformLength, 0, combinedBytes, offset, 4);
        offset += 4;
        System.arraycopy(transformBytes, 0, combinedBytes, offset, transformBytes.length);
        offset += transformBytes.length;
        System.arraycopy(iv.getIV(), 0, combinedBytes, offset, iv.getIV().length);
        offset += iv.getIV().length;
        System.arraycopy(encryptedBytes, 0, combinedBytes, offset, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combinedBytes);

    }

    public String decryptContent(SecretKey key) throws Exception{
            System.out.println("here");
            byte[] combinedBytes = Base64.getDecoder().decode(this.getFileContent());
            System.out.println("here");
            byte[] lengthBytes = new byte[4];
            System.arraycopy(combinedBytes, 0, lengthBytes, 0, 4);
            int transformLength = ByteBuffer.wrap(lengthBytes).getInt();
            byte[] transformBytes = new byte[transformLength];
            System.arraycopy(combinedBytes, 4, transformBytes, 0, transformLength);

            String transformation = new String(transformBytes);
            System.out.println(transformation);
            String[] params = transformation.split("/");
            this.setCipherAlgorithm(params[0]);
            this.setBlockMode(params[1]);
            this.setPadding(params[2]);

            int ivSize = this.getIvSize();
            byte[] iv = new byte[ivSize];
            int ivOffset = 4 + transformLength;
            System.arraycopy(combinedBytes, ivOffset, iv, 0, ivSize);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            this.setIV(ivSpec);

            byte[] encryptedContent = new byte[combinedBytes.length - ivOffset - ivSize];
            System.arraycopy(combinedBytes, ivOffset + ivSize, encryptedContent, 0, encryptedContent.length);


            Cipher cipher = Cipher.getInstance(transformation, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedContent);
            return new String(decryptedBytes);




    }



    public void initState() throws IOException{
        this.setSavedContent(this.getFileContent());


    }
    public String getFileContent() throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
        String line;
        while((line = reader.readLine()) != null){
            sb.append(line);
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

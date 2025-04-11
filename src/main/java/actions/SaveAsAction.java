package actions;

import components.CipherDialog;
import controllers.MainPanelController;
import models.FileModel;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;

/*
* save-as action - implements the shortcut and general action handler for saving contents of a given tab(AT LEAST to a new
* model, there are two cases for this action to be used:
*   saving an entirely new file from a tab that was created with the new action
*   Saving a NEW copy of an existing tab with a file already on disk
*/

public class SaveAsAction extends AbstractMenuAction{

    //constructor
    public SaveAsAction(MainPanelController controller){
        super(controller, "Save As");
        initShortcut();

    }

    //shortcut implementation for ctrl + shift + S
    public void initShortcut(){

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.SHIFT_DOWN_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        putValue(SHORT_DESCRIPTION, "Save file as");

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try{
            String content = this.getController().getSelectedTextAreaContent();
            int index = this.getController().getGui().getTabbedPane().getSelectedIndex();
            String currentTitle = this.getController().getGui().getTabbedPane().getTitleAt(index);
            FileModel currentModel = this.getController().getFileModelForTab(currentTitle);

            File file = this.getController().getGui().chooseFileToSave();
            if(file != null){

                String newTitle = file.getName();
                if(currentModel == null){
                    this.handleNewFile(newTitle, file, content, index);
                }else{
                    this.handleFileCopy(currentTitle, newTitle, file, content);
                }
            }
        }catch(Exception e){
            System.out.printf("Error saving file: %s", e.getMessage());
        }


    }

    private void handleNewFile(String newTitle, File file, String content, int index) throws Exception{
        FileModel newModel = new FileModel(file);
        this.getController().putFileModelForTab(newTitle, newModel);
        this.getController().getGui().setTabTitle(index, newTitle);

        boolean shouldEncrypt = promptForEncryption(newTitle);
        if (shouldEncrypt) {

            this.getController().getGui().showCipherDialog(newTitle);
            SecretKey key = this.getController().getGui().getKeyController().getKey(newTitle);
            if(key != null){
                String encrypted = newModel.encryptContent(key, content);
                String status = newModel.saveContent(encrypted);
                this.getController().updateStatus(status);
            }else{
                String status = newModel.saveContent(content);
                this.getController().updateStatus(status);
            }

        }else{
            String status = newModel.saveContent(content);
            this.getController().updateStatus(status);
        }
    }

    private void handleFileCopy(String originalTitle, String newTitle, File file, String content) throws Exception{
        FileModel newModel = new FileModel(file);
        this.getController().putFileModelForTab(newTitle, newModel);
        this.getController().addNewTabToView(newTitle, content);

        boolean hasOriginalKey = this.getController().getKeyStoreController().keyExists(originalTitle);
        if (hasOriginalKey) {
            int choice = showKeyOptionDialog();
            switch(choice){
                case 0:
                    handleCreateNewKey(newModel, newTitle, content);
                    break;
                case 1:
                    handleSameKeyUse(originalTitle, newModel, newTitle, content);
                    break;
                case 2:
                default:
                    String status = newModel.saveContent(content);
                    this.getController().updateStatus(status);
                    break;
            }
        }else{
            String status = newModel.saveContent(content);
            this.getController().updateStatus(status);
        }
    }

    private void handleCreateNewKey(FileModel newModel, String newTitle, String content) throws Exception{
        this.getController().getGui().showCipherDialog(newTitle);

        if (this.getController().getKeyStoreController().keyExists(newTitle)) {
            SecretKey newKey = this.getController().getKeyStoreController().getKey(newTitle);
            if(newKey != null){
                String encrypted = newModel.encryptContent(newKey, content);
                String status = newModel.saveContent(encrypted);
                this.getController().updateStatus(status);
            }
        }else{
            String status = newModel.saveContent(content);
            this.getController().updateStatus(status);
        }
    }

    private void handleSameKeyUse(String originalTitle, FileModel newModel, String newTitle, String content) throws Exception{
        SecretKey oldKey = this.getController().getKeyStoreController().getKey(originalTitle);
        if(oldKey != null){
            this.getController().getKeyStoreController().storeKey(oldKey, newTitle);

            String encrypted = newModel.encryptContent(oldKey, content);
            String status = newModel.saveContent(encrypted);
            this.getController().updateStatus(status);
        }
    }

    private boolean promptForEncryption(String fileName){
        String message = "<html><div style='text-align: center'>Would you like to create an encryption key for this file?</div></html>";
        String dialogTitle = String.format("create key for %s", fileName);
        int choice = JOptionPane.showConfirmDialog(this.getController().getGui(), message, dialogTitle, JOptionPane.YES_NO_OPTION);

        return choice == JOptionPane.YES_OPTION;
    }



    public int showKeyOptionDialog(){
        String[] options = {"create new key", "use same key", "cancel"};

        return JOptionPane.showOptionDialog(this.getController().getGui(),
                "please choose an option",
                "key setup",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

}

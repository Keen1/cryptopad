package handlers.tabs;

import controllers.GuiController;
import models.FileModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/*
* TODO: refactor this to an action and delete the handler
*/
public class CloseTabHandler implements ActionListener {

    private JTabbedPane tabPane;
    private GuiController controller;
    private static final String UNSAVED_CHANGES_PROMPT = "has unsaved changes. Do you want to change them?";

    public CloseTabHandler(JTabbedPane tabPane, GuiController controller){

        this.tabPane = tabPane;
        this.controller = controller;

    }

    public GuiController getController(){

        return this.controller;

    }

    public JTabbedPane getTabPane(){

        return this.tabPane;
    }

    @Override
    public void actionPerformed(ActionEvent event){

        JButton sourceBtn = (JButton) event.getSource();
        JPanel titlePanel = (JPanel)sourceBtn.getParent();
        JLabel label = (JLabel)titlePanel.getComponent(0);
        String title = label.getText();
        int index = this.getTabPane().indexOfTabComponent(titlePanel);
        FileModel model = this.getController().getFileModelForTab(title);
        //if the model doesn't exist and the user is trying to close the tab
        if(model == null){
            this.getTabPane().removeTabAt(index);
        }else{
            if(this.getController().getFileModelForTab(title).hasUnsavedChanges()){

                String prompt = title + UNSAVED_CHANGES_PROMPT;
                String[] options = {"Save", "Don't Save", "Cancel"};
                String unsavedChanges = "Unsaved Changes";

                int choice = JOptionPane.showOptionDialog(this.getController().getGui(),
                        prompt,
                        unsavedChanges,
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0]);

                if(choice == JOptionPane.YES_OPTION){

                    try{

                        this.getController().saveTabContent(title);
                        this.getController().removeModelForTab(title);

                        if(index != -1){

                            this.getTabPane().removeTabAt(index);

                        }


                    }catch(IOException e){

                        System.out.printf("error saving file: %s", e.getMessage());

                    }
                }

                else if(choice == JOptionPane.NO_OPTION){

                    this.getController().removeModelForTab(title);

                    if(index != -1){

                        this.getTabPane().removeTabAt(index);

                    }
                }

            }else{

                this.getController().removeModelForTab(title);

                if(index != -1){

                    this.getTabPane().removeTabAt(index);

                }
            }

        }


    }

}

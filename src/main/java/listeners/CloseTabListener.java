package listeners;

import controllers.GuiController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseTabListener implements ActionListener {
    private JTabbedPane tabPane;
    private GuiController controller;

    public CloseTabListener(JTabbedPane tabPane, GuiController controller){
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

        /*
         * TODO need to conduct a check to see if the file has unsaved changes
         *  inform user that unsaved changes will be lost with dialog
         *   give user option to save changes and close, or not
         */
        if(this.getController().getFileModelForTab(title).hasUnsavedChanges()){

        }
        this.getController().removeModelForTab(title);

        int index = this.getTabPane().indexOfTabComponent(titlePanel);
        if(index != -1){
            this.getTabPane().removeTabAt(index);
        }

        sourceBtn.removeActionListener(this);

    }

}

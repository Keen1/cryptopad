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

    @Override
    public void actionPerformed(ActionEvent event){

        JButton sourceBtn = (JButton) event.getSource();
        JPanel titlePanel = (JPanel)sourceBtn.getParent();
        JLabel label = (JLabel)titlePanel.getComponent(0);
        String title = label.getText();
        this.controller.removeModelForTab(title);

        int index = tabPane.indexOfTabComponent(titlePanel);
        if(index != -1){
            tabPane.removeTabAt(index);
        }

        sourceBtn.removeActionListener(this);

    }

}

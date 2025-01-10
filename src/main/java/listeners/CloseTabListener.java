package listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CloseTabListener implements ActionListener {
    private JTabbedPane tabPane;

    public CloseTabListener(JTabbedPane tabPane){
        this.tabPane = tabPane;
    }

    @Override
    public void actionPerformed(ActionEvent event){
        JButton sourceBtn = (JButton) event.getSource();
        JPanel titlePanel = (JPanel)sourceBtn.getParent();
        int index = tabPane.indexOfTabComponent(titlePanel);
        if(index != -1){
            tabPane.removeTabAt(index);
        }
        sourceBtn.removeActionListener(this);

    }

}

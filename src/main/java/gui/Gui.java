package gui;

import javax.swing.*;
import java.awt.*;

public class Gui {
    private JToolBar toolBar;
    private JButton fileButton;
    private JButton editButton;
    private JTabbedPane tabPane;
    private JFrame frame;

    public Gui(){
        initComponents();
    }



    public JTabbedPane getTabbedPane(){

        if(this.tabPane == null){
            initTabbedPane();
        }
        return this.tabPane;

    }
    public JFrame getFrame(){

        if(this.frame == null){
            initFrame();
        }
        return this.frame;
    }

    private void initFrame(){

        this.frame = new JFrame("cryptopad");
        this.frame.setPreferredSize(new Dimension(800, 1000));
        this.frame.setLayout(new BorderLayout());

    }

    private void initTabbedPane(){

        this.tabPane  = new JTabbedPane();
        tabPane.add("untitled", initUntitledTextArea());
    }


    private JPanel initUntitledTextArea(){

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    public JToolBar getToolBar(){
        if(this.toolBar == null){
            initToolBar();
        }
        return this.toolBar;
    }

    private void initToolBar(){

        this.toolBar = new JToolBar();
        this.toolBar.add(getFileButton());
        this.toolBar.add(getEditButton());

    }

    public JButton getFileButton(){

        if(this.fileButton == null){
            initFileButton();
        }
        return this.fileButton;
    }

    private void initFileButton(){

        this.fileButton = new JButton("file");

    }
    public JButton getEditButton(){

        if(this.editButton == null){
            initEditButton();
        }
        return this.editButton;
    }

    private void initEditButton(){
        this.editButton = new JButton("edit");
    }

    private JMenuBar getMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("file");
        JMenuItem item1 = new JMenuItem("item1");
        JMenuItem item2 = new JMenuItem("item2");
        JMenuItem item3 = new JMenuItem("item3");
        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menuBar.add(menu);



        return menuBar;
    }

    private void initComponents(){
        JFrame frame = this.getFrame();
        frame.add(getMenuBar(), BorderLayout.NORTH);
        frame.add(getTabbedPane(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}

package gui;

import controllers.GuiController;
import listeners.CloseTabListener;
import listeners.menu.UnsavedChangesHandler;
import models.FileModel;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/*
* gui view class.*/
public class Gui {

    private JToolBar toolBar;
    private JButton fileButton;
    private JButton editButton;
    private JTabbedPane tabPane;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem saveItem;
    private JMenuItem openItem;
    private JMenuItem saveAsItem;
    private JMenuItem cipherItem;
    private GuiController controller;

    public Gui(){
        initComponents();
        this.controller = new GuiController(this, new FileModel());
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
    public void addNewTab(String title, String content){
        //create the panel for the content provided
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        this.getTabbedPane().add(title, panel);
        this.getTabbedPane().setSelectedComponent(panel);

        //create the tab's title panel with the close button
        int index = this.getTabbedPane().indexOfTab(title);
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        JLabel tabTitle = new JLabel(title);
        JButton closeButton = new JButton("X");

        //add the listener to the tab's close button
        closeButton.addActionListener(new CloseTabListener(this.getTabbedPane(), this.controller));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        titlePanel.add(tabTitle, gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        titlePanel.add(closeButton, gbc);
        this.getTabbedPane().setTabComponentAt(index, titlePanel);

        //add a document handler to the textArea to track changes to content
        textArea.getDocument()
                .addDocumentListener(new UnsavedChangesHandler(textArea, this.controller.getFileModelForTab(title)));





    }
    public JTextArea getTextAreaForSelectedTab(){
        JPanel panel  = (JPanel)this.getTabbedPane().getSelectedComponent();
        JScrollPane scrollPane = (JScrollPane)panel.getComponent(0);
        return (JTextArea)scrollPane.getViewport().getView();
    }

    public File chooseFileToOpen(){
        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(this.getFrame());
        if(res == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }





    private void initTabbedPane(){

        this.tabPane  = new JTabbedPane();
        addNewTab("untitled", "");
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

    public JMenuBar getMenuBar(){
        if(this.menuBar == null){
             initMenuBar();
        }
        return this.menuBar;
    }

    private void initOpenItem(){
        this.openItem = new JMenuItem("Open...");
    }
    public JMenuItem getOpenItem(){
        if(this.openItem == null){
            initOpenItem();
        }
        return openItem;
    }
    public void initSaveItem(){
        this.saveItem = new JMenuItem("Save");
    }
    public JMenuItem getSaveItem(){
        if(this.saveItem == null){
            initSaveItem();
        }
        return this.saveItem;
    }
    private void initSaveAsItem(){
        this.saveAsItem = new JMenuItem("Save As...");
    }
    public JMenuItem getSaveAsItem(){
        if(this.saveAsItem == null){
            initSaveAsItem();
        }
        return this.saveAsItem;
    }
    private void initCipherItem(){
        this.cipherItem = new JMenuItem("Cipher...");
    }
    public JMenuItem getCipherItem(){
        if(this.cipherItem == null){
            initCipherItem();
        }
        return this.cipherItem;
    }

    private void initMenuBar(){
        this.menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(getOpenItem());
        menu.add(getSaveItem());
        menu.add(getSaveAsItem());
        menu.add(getCipherItem());
        this.menuBar.add(menu);
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

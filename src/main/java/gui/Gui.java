package gui;

import controllers.GuiController;
import listeners.CloseTabListener;
import listeners.menu.UnsavedChangesHandler;
import models.FileModel;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/*
* gui view class.
*/
public class Gui {
    //basic components of our gi
    private JToolBar toolBar;
    private JButton fileButton;
    private JMenuBar menuBar;
    private JMenuItem saveItem;
    private JMenuItem openItem;
    private JMenuItem saveAsItem;
    private JMenuItem newItem;
    private JMenuItem cipherItem;
    private JButton editButton;
    private JTabbedPane tabPane;
    private JFrame frame;
    private GuiController controller;

    //constructor
    public Gui(){
        initComponents();
    }

    //get the controller associated with the gui
    public GuiController getController(){
        if(this.controller == null){
            initController();
        }
        return this.controller;
    }

    //init the controller
    public void initController(){
        this.controller = new GuiController(this, new FileModel());
    }

    //get the tabbed pane where all our text files are opened and displayed
    public JTabbedPane getTabbedPane(){

        if(this.tabPane == null){
            initTabbedPane();
        }
        return this.tabPane;

    }

    //init the tab pane
    private void initTabbedPane(){

        this.tabPane  = new JTabbedPane();
        //addNewTab("untitled", "");
    }

    //get the main gui frame
    public JFrame getFrame(){

        if(this.frame == null){
            initFrame();
        }
        return this.frame;
    }

    //init the main gui frame
    private void initFrame(){

        this.frame = new JFrame("cryptopad");
        this.frame.setPreferredSize(new Dimension(800, 1000));
        this.frame.setLayout(new BorderLayout());
    }

    //add a new tab given a title and the content for the tab
    public void addNewTab(String title, String content){

        //create the panel for the content provided
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
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
        closeButton.addActionListener(new CloseTabListener(this.getTabbedPane(), this.getController()));
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
                .addDocumentListener(new UnsavedChangesHandler(textArea, this.getController().getFileModelForTab(title)));
    }

    //get the content of a tab given a title
    public String getTabContent(String title){

        int index = this.getTabbedPane().indexOfTab(title);
        JTextArea textArea = getTextAreaForIndex(index);
        if(textArea != null){
            return textArea.getText();
        }
        return null;

    }

    //get the text area for a tab given an index
    public JTextArea getTextAreaForIndex(int index){

        if(index != -1){
            JPanel panel = (JPanel) this.getTabbedPane().getComponentAt(index);
            JScrollPane scrollPane = (JScrollPane) panel.getComponent(0);
            return (JTextArea)scrollPane.getViewport().getView();
        }
        return null;
    }

    //get the text area of the selected focused tab
    public JTextArea getTextAreaForSelectedTab(){

        JPanel panel  = (JPanel)this.getTabbedPane().getSelectedComponent();
        JScrollPane scrollPane = (JScrollPane)panel.getComponent(0);
        return (JTextArea)scrollPane.getViewport().getView();
    }

    //get a file that the user has chosen to open through a dialog
    public File chooseFileToOpen(){

        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(this.getFrame());
        if(res == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    //get the menu bar
    public JMenuBar getMenuBar(){
        if(this.menuBar == null){
             initMenuBar();
        }
        return this.menuBar;
    }

    //init the open menu item
    private void initOpenItem(){
        this.openItem = new JMenuItem("Open...");
    }

    //get the open menu item
    public JMenuItem getOpenItem(){

        if(this.openItem == null){
            initOpenItem();
        }
        return openItem;
    }

    //init the save menu item
    public void initSaveItem(){
        this.saveItem = new JMenuItem("Save");
    }

    //get the save menu item
    public JMenuItem getSaveItem(){

        if(this.saveItem == null){
            initSaveItem();
        }
        return this.saveItem;
    }

    //init the save menu item
    private void initSaveAsItem(){
        this.saveAsItem = new JMenuItem("Save As...");
    }

    //get the save as menu item
    public JMenuItem getSaveAsItem(){

        if(this.saveAsItem == null){
            initSaveAsItem();
        }
        return this.saveAsItem;
    }

    //init the cipher menu item
    private void initCipherItem(){
        this.cipherItem = new JMenuItem("Cipher...");
    }

    //get the cipher menu item
    public JMenuItem getCipherItem(){

        if(this.cipherItem == null){
            initCipherItem();
        }
        return this.cipherItem;
    }

    //init the new menu item
    private void initNewItem(){
        this.newItem = new JMenuItem("New...");
    }

    //get the new menu item
    public JMenuItem getNewItem(){

        if(this.newItem == null){
            initNewItem();
        }
        return this.newItem;
    }

    //init the menu bar. adds all of our menu items under the appropriate menu
    private void initMenuBar(){

        this.menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(getNewItem());
        menu.add(getOpenItem());
        menu.add(getSaveItem());
        menu.add(getSaveAsItem());
        menu.add(getCipherItem());
        this.menuBar.add(menu);

    }

    //init all components of the gui.
    //any new component feature must be called here to be initialized and added to the frame
    private void initComponents(){

        initController();
        JFrame frame = this.getFrame();
        frame.add(getMenuBar(), BorderLayout.NORTH);
        frame.add(getTabbedPane(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

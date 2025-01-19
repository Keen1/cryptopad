package gui;

import actions.SaveAction;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatMaterialDesignDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import components.StatusLabel;
import controllers.GuiController;
import listeners.tabs.CloseTabHandler;
import listeners.menu.UnsavedChangesHandler;
import javax.swing.*;
import java.awt.*;
import java.io.File;

import static javax.swing.Action.ACCELERATOR_KEY;

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

    private JMenu themeMenu;

    private StatusLabel statusLabel;

    private SaveAction saveAction;

    //constructor
    public Gui(){
        initComponents();
    }

    private void initStatusLabel(){
        this.statusLabel = new StatusLabel();
    }

    public StatusLabel getStatusLabel(){
        if(this.statusLabel == null){
            initStatusLabel();
        }
        return this.statusLabel;
    }

    private void initSaveAction(){
        this.saveAction = new SaveAction(this.getController());
    }

    public SaveAction getSaveAction(){
        if(this.saveAction == null){
            initSaveAction();
        }
        return this.saveAction;
    }

    public void updateStatus(String update){
        this.getStatusLabel().setStatusText(update);
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
        this.controller = new GuiController(this);
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

    private void addTextAreaShortcut(JTextArea textArea){
        SaveAction saveAction = this.getSaveAction();
        KeyStroke shortcut = (KeyStroke)saveAction.getValue(ACCELERATOR_KEY);
        textArea.getInputMap(JComponent.WHEN_FOCUSED).put(shortcut, "save");
        textArea.getActionMap().put("save", saveAction);
    }

    //add a new tab given a title and the content for the tab
    public void addNewTab(String title, String content){

        //create the panel for the content provided
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.addTextAreaShortcut(textArea);
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
        closeButton.addActionListener(new CloseTabHandler(this.getTabbedPane(), this.getController()));
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
        this.saveItem.setAction(this.getSaveAction());

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
        this.initThemeMenu();
        this.menuBar.add(this.getThemeMenu());


    }

    private void initThemeMenu(){
        this.themeMenu = new JMenu("Theme");
        addThemeMenuItems();

    }
    private JMenu getThemeMenu(){
        if(this.themeMenu == null){
            initThemeMenu();
        }
        return this.themeMenu;
    }

    //TODO need to add and support these themes more programmatically

    private void addThemeMenuItems(){
        addThemeItem("Light", new FlatLightLaf());
        addThemeItem("Dark", new FlatDarkLaf());
        addThemeItem("Intellij(light)", new FlatIntelliJLaf());
        addThemeItem("Darcula", new FlatDarculaLaf());
        addThemeItem("Arc(dark)", new FlatArcDarkIJTheme());
        addThemeItem("Arc(light)", new FlatArcIJTheme());
        addThemeItem("Material(dark)", new FlatMaterialDesignDarkIJTheme());
        addThemeItem("Material(darker)", new FlatMaterialDarkerIJTheme());
        addThemeItem("Material(light)", new FlatMaterialLighterIJTheme());
        addThemeItem("Material(oceanic)", new FlatMaterialOceanicIJTheme());
        addThemeItem("Material(pale night)", new FlatMaterialPalenightIJTheme());
        addThemeItem("Material(deep ocean)", new FlatMaterialDeepOceanIJTheme());
        addThemeItem("Solarized(light)", new FlatSolarizedLightIJTheme());
        addThemeItem("Solarized(dark)", new FlatSolarizedDarkIJTheme());
        addThemeItem("Github(light)", new FlatGitHubIJTheme());
        addThemeItem("Github(dark)", new FlatGitHubDarkIJTheme());
        addThemeItem("Monokai", new FlatMonokaiProIJTheme());


    }

    private void addThemeItem(String name, LookAndFeel laf){
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> {
            try{
                UIManager.setLookAndFeel(laf);
                SwingUtilities.updateComponentTreeUI(this.getFrame());
            }catch(UnsupportedLookAndFeelException ex){
                System.out.printf("Error setting look and feel: %s", ex.getMessage());
            }
        });

        this.themeMenu.add(item);
    }

    //init all components of the gui.
    //any new component feature must be called here to be initialized and added to the frame
    private void initComponents(){

        initController();
        JFrame frame = this.getFrame();
        frame.add(getMenuBar(), BorderLayout.NORTH);
        frame.add(getTabbedPane(), BorderLayout.CENTER);
        frame.add(getStatusLabel(), BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

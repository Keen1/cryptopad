package components;

import actions.*;
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
import controllers.MainPanelController;
import controllers.SecretKeyController;
import handlers.tabs.CloseTabHandler;
import handlers.menu.UnsavedChangesHandler;
import models.KeyStoreResultModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.KeyStore;

import static javax.swing.Action.ACCELERATOR_KEY;

/*
* gui view class.
*/

public class MainPanel extends JPanel{

    //basic components of our gui
    private JMenuBar menuBar;
    private JMenuItem saveItem;
    private JMenuItem openItem;
    private JMenuItem saveAsItem;
    private JMenuItem newItem;
    private JMenuItem cipherItem;
    private JMenuItem encryptItem;
    private JMenuItem decryptItem;
    private JTabbedPane tabPane;
    private JFrame frame;

    private MainPanelController controller;
    private SecretKeyController keyController;

    private JMenu themeMenu;

    private StatusLabel statusLabel;

    private SaveAction saveAction;
    private SaveAsAction saveAsAction;
    private OpenAction openAction;
    private NewAction newAction;






    //constructor
    public MainPanel(KeyStoreResultModel model){
        initSecretKeyController(model);
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

    private void initSaveAsAction(){
        this.saveAsAction = new SaveAsAction(this.getController());
    }
    public SaveAsAction getSaveAsAction(){
        if(this.saveAsAction == null){
            initSaveAsAction();
        }
        return this.saveAsAction;
    }

    private void initSaveAction(){
        this.saveAction = new SaveAction(this.getController());
    }

    private void initOpenAction(){
        this.openAction = new OpenAction(this.getController());
    }

    public OpenAction getOpenAction(){
        if(this.openAction == null){
            initOpenAction();
        }
        return this.openAction;
    }
    private void initNewAction(){
        this.newAction = new NewAction(this.getController());
    }
    public NewAction getNewAction(){
        if(this.newAction == null){
            initNewAction();
        }
        return this.newAction;
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
    public MainPanelController getController(){
        if(this.controller == null){
            initController();
        }
        return this.controller;
    }

    //init the controller
    public void initController(){
        this.controller = new MainPanelController(this);
    }

    public SecretKeyController getKeyController(){
        return this.keyController;
    }
    public void initSecretKeyController(KeyStoreResultModel keyStoreModel){
        this.keyController = new SecretKeyController(keyStoreModel.getKeyStore(), keyStoreModel.getPw());
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
        this.tabPane.addChangeListener(event ->{
            this.getCipherItem().setEnabled(this.getTabbedPane().getTabCount() > 0);
        });
        //addNewTab("untitled", "");
    }



    //TODO this should be refactored to specifcally be the addSaveAction shortcut
    private void addTextAreaShortcut(JTextArea textArea){
        SaveAction saveAction = this.getSaveAction();
        KeyStroke shortcut = (KeyStroke)saveAction.getValue(ACCELERATOR_KEY);
        textArea.getInputMap(JComponent.WHEN_FOCUSED).put(shortcut, "save");
        textArea.getActionMap().put("save", saveAction);


    }

    private void addSaveAsShortcut(JTextArea textArea){
        SaveAsAction action = this.getSaveAsAction();
        KeyStroke shortcut = (KeyStroke)action.getValue(ACCELERATOR_KEY);
        textArea.getInputMap(JComponent.WHEN_FOCUSED).put(shortcut, "Save As");
        textArea.getActionMap().put("Save As", action);
    }



    //where should we call this???? ^^^ save Action shortcut is called every time we add a new tab(since this is where
    //the shortcut is first needed and where it is called from -- when the user is typing in the text area and wants to
    //save but not navigate the menu. I think this should just be called in the init frame method honestly.


    //I'm more convinced now that this should be called in the init frame method. This is a FUNCTION of initializing the
    //frame and should thus be called when the frame is being initialized
    private void addOpenShortcut(){

        OpenAction openAction = this.getOpenAction();
        KeyStroke shortcut = (KeyStroke)openAction.getValue(ACCELERATOR_KEY);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(shortcut, "open");
        getActionMap().put("open", openAction);

        JMenuItem openItem = this.getOpenItem();
        openItem.setAction(openAction);

    }

    //add the new action
    private void addNewShortcut(){
        NewAction newAction = this.getNewAction();
        KeyStroke shortcut = (KeyStroke)newAction.getValue(ACCELERATOR_KEY);
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(shortcut, "new");
        getActionMap().put("new", newAction);

        JMenuItem newItem = this.getNewItem();
        newItem.setAction(newAction);
    }

    //add a new tab given a title and the content for the tab
    public void addNewTab(String title, String content){

        //create the panel for the content provided
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(content);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.addTextAreaShortcut(textArea);
        this.addSaveAsShortcut(textArea);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        this.getTabbedPane().add(title, panel);
        this.getTabbedPane().setSelectedComponent(panel);

        //create the title panel with a close button
        int index = this.getTabbedPane().getSelectedIndex();
        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setOpaque(false);
        JLabel tabTitle = new JLabel(title);
        JButton closeButton = new JButton("x");

        //add the listener to the tab's close button
        closeButton.addActionListener(new CloseTabHandler(this.getTabbedPane(), this.getController()));

        //set the components for the tab title panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        titlePanel.add(tabTitle, gbc);
        gbc.gridx++;
        gbc.weightx = 0;
        titlePanel.add(closeButton, gbc);
        this.getTabbedPane().setTabComponentAt(index, titlePanel);

        //set focus at the end of the text area so user can immediately write new content
        SwingUtilities.invokeLater(textArea::requestFocusInWindow);
        textArea.setCaretPosition(textArea.getDocument().getLength());

        //attach the unsaved changes handler
        textArea.getDocument()
                .addDocumentListener(new UnsavedChangesHandler(textArea, this.getController()));

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

    //TODO -- move implementation for unsaved changes dialog from handler to gui
    public void showUnsavedChangesDialog(){

    }

    //get a file that the user has chosen to open through a dialog
    public File chooseFileToOpen(){

        JFileChooser fileChooser = new JFileChooser();
        int res = fileChooser.showOpenDialog(this);
        if(res == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public File chooseFileToSave(){
        File file = null;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("save a new file");

        int res = fileChooser.showSaveDialog(this);

        if(res == JFileChooser.APPROVE_OPTION){
            file  = fileChooser.getSelectedFile();
            return file;
        }
        return file;
    }

    public int showNoKeyFoundDialog(String title){
        String message = String.format("<html><div style='text-align: center'>No encryption key was found for this file:%s" +
                " Would you like to create one?</div></html>", title);
        String dialogTitle = String.format("no key found for %s", title);
        return JOptionPane.showConfirmDialog(this, message, dialogTitle, JOptionPane.YES_NO_OPTION);
    }

    public void showCipherDialog(){
        CipherDialog dialog = new CipherDialog(this.getKeyController(), this.getController());
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public void showCipherDialog(String selectedTab){
        CipherDialog dialog = new CipherDialog(this.getKeyController(), this.getController());
        dialog.setLocationRelativeTo(this);
        dialog.setSelectedTabTitle(selectedTab);

    }

    public void attachUnsavedChangesHandler(JTextArea textArea){
        textArea.getDocument().addDocumentListener(new UnsavedChangesHandler(textArea, this.getController()));
    }

    public void setTabTitle(int index, String title){
        this.getTabbedPane().setTitleAt(index, title);
        JPanel titlePanel = (JPanel)this.getTabbedPane().getTabComponentAt(index);
        JLabel tabLabel = (JLabel)titlePanel.getComponent(0);
        tabLabel.setText(title);
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
        this.saveAsItem.setAction(this.getSaveAsAction());
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
        //disable this item by default(on open and when no tabs are open
        this.cipherItem.setEnabled(false);
        //only enable the cipher menu item if a tab is actually open

        this.cipherItem.addActionListener(event -> {
            CipherDialog cipherChooser = new CipherDialog(this.getKeyController(), this.getController());
            cipherChooser.setVisible(true);
        });
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


    /*
    * TODO need to class this out or do something more programmatically.
    *  There are a crapload of themes in the flatlaf project so supporting them this way is probably a bad idea.
    */
    
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
                SwingUtilities.updateComponentTreeUI(this);
            }catch(UnsupportedLookAndFeelException ex){
                System.out.printf("Error setting look and feel: %s", ex.getMessage());
            }
        });

        this.themeMenu.add(item);
    }

    //init all components of the gui.
    //any new component feature must be called here to be initialized and added to the frame
    private void initComponents(){
        setLayout(new BorderLayout());
        initController();
        add(getMenuBar(), BorderLayout.NORTH);
        add(getTabbedPane(), BorderLayout.CENTER);
        add(getStatusLabel(), BorderLayout.SOUTH);
        addOpenShortcut();
        addNewShortcut();

    }
}

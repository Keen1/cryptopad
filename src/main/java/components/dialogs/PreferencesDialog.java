package components.dialogs;

import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import controllers.PreferencesController;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import javax.swing.UIManager.LookAndFeelInfo;

/*
* Preferences dialog -- allows user to customize the application.
*/

public class PreferencesDialog extends JDialog {

    //components
    private JComboBox<String> themeComboBox;
    private JComboBox<String> fontFamilyComboBox;
    private JFormattedTextField fontSizeField;
    private JButton applyButton;
    private JButton cancelButton;
    private HashMap<String, String> lafMap;

    //preferences controller
    private final PreferencesController controller;


    //default constructor
    public PreferencesDialog(PreferencesController controller, JFrame parentFrame, String title, boolean modal){
        super(parentFrame, title, modal);
        this.controller = controller;
        initComponents();
    }

    //getter for controller
    public PreferencesController getController(){
        return this.controller;
    }

    //initiate the components and pack the dialog
    public void initComponents(){

        setLayout(new BorderLayout());

        JPanel selectionPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel fontSizeLabel = new JLabel("font size: ");
        JLabel themeLabel = new JLabel("theme: ");
        JLabel fontFamilyLabel = new JLabel("font: ");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        selectionPanel.add(themeLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getThemeComboBox());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        selectionPanel.add(fontFamilyLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getFontFamilyComboBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        selectionPanel.add(fontSizeLabel, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getFontSizeField(), gbc);

        add(selectionPanel, BorderLayout.SOUTH);

        buttonPanel.add(getApplyButton());
        buttonPanel.add(getCancelButton());

        add(buttonPanel, BorderLayout.SOUTH);

        pack();

    }

    //getter for the cancel button
    public JButton getCancelButton(){

        if(this.cancelButton == null){
            initCancelButton();
        }

        return this.cancelButton;
    }

    //init function for the cancel button
    private void initCancelButton(){
        this.cancelButton = new JButton("cancel");
    }

    //getter for apply button
    public JButton getApplyButton(){

        if(this.applyButton == null){
            initApplyButton();
        }

        return this.applyButton;
    }

    //init function for apply button
    private void initApplyButton(){
        this.applyButton = new JButton("apply");
    }


    //getter for the font size field
    public JFormattedTextField getFontSizeField(){

        if(this.fontSizeField == null){
            initFontSizeField();
        }

        return this.fontSizeField;
    }

    //init function for font size field
    //TODO -- need to test and make sure these formatters work currently it just lets you type in whatever doesn't restrict
    private void initFontSizeField(){
        this.fontSizeField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        this.fontSizeField.setValue(this.getController().getFontSize());

    }

    //getter for the font family combo box
    public JComboBox<String> getFontFamilyComboBox(){

        if(this.fontFamilyComboBox == null){
            initFontFamilyComboBox();
        }

        return this.fontFamilyComboBox;
    }

    //init function for the font family combo box
    private void initFontFamilyComboBox(){

        this.fontFamilyComboBox = new JComboBox<>(getFontNames());
        String currentFont = this.getController().getFontFamily();
        this.fontFamilyComboBox.setSelectedItem(currentFont);

    }

    //getter for local graphics env fonts available
    public String[] getFontNames(){
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    //getter for the theme combo box
    public JComboBox<String> getThemeComboBox(){

        if(this.themeComboBox == null){
            initThemeComboBox();
        }

        return this.themeComboBox;
    }

    //init function for the theme combo box
    private void initThemeComboBox(){
        this.themeComboBox = new JComboBox<>(getLafNames());

    }

    //utility function to retrieve look and feel names
    private String[] getLafNames(){
        return this.getLafMap().keySet().toArray(new String[0]);
    }

    //getter for the selected item of the theme combo box
    public String getSelectedTheme(){
        return (String)this.getThemeComboBox().getSelectedItem();
    }

    //getter for the selected item of the font family combo box
    public String getSelectedFontFamily(){
        return (String)this.getFontFamilyComboBox().getSelectedItem();
    }

    //get the inputted font size from the font size field
    public int getSelectedFontSize(){
        return Integer.parseInt(this.getFontSizeField().getText());
    }

    //getter for  the look and feel map
    public HashMap<String, String> getLafMap(){

        if(this.lafMap == null){
            initLafMap();
        }

        return this.lafMap;
    }
    //init function for the look and feel map
    private void initLafMap(){
        this.lafMap = new HashMap<>();
        //TODO -- check if there is a way to not hardcode this.
        this.lafMap.put("FlatLaf Light", "com.formdev.flatlaf.FlatLightLaf");
        this.lafMap.put("FlatLaf Dark", "com.formdev.flatlaf.FlatDarkLaf");
        this.lafMap.put("FlatLaf Intellij", "com.formdev.flatlaf.FlatIntellijLaf");
        this.lafMap.put("FlatLaf Darcula", "com.formdev.flatlaf.FlatDarculaLaf");

        LookAndFeelInfo[] installedLaf = UIManager.getInstalledLookAndFeels();
        for(LookAndFeelInfo info : installedLaf){
            this.lafMap.put(info.getName(), info.getClassName());
        }

        for(FlatAllIJThemes.FlatIJLookAndFeelInfo info : FlatAllIJThemes.INFOS){
            this.lafMap.put(info.getName(), info.getClassName());
        }
        this.getController().setLafMap(this.lafMap);

    }
}

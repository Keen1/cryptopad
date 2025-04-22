package components;

import com.formdev.flatlaf.intellijthemes.FlatAllIJThemes;
import controllers.PreferencesController;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.HashMap;
import javax.swing.UIManager.LookAndFeelInfo;

public class PreferencesDialog extends JDialog {

    private JComboBox<String> themeComboBox;
    private JComboBox<String> fontFamilyComboBox;
    private JFormattedTextField fontSizeField;
    private JButton applyButton;
    private JButton cancelButton;
    private HashMap<String, String> lafMap;

    private final PreferencesController controller;

    public PreferencesDialog(PreferencesController controller, JFrame parentFrame, String title, boolean modal){
        super(parentFrame, title, modal);
        this.controller = controller;
        initComponents();
    }

    public PreferencesController getController(){
        return this.controller;
    }

    //TODO init all the components and lay out the dialog
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


    public JButton getCancelButton(){

        if(this.cancelButton == null){
            initCancelButton();
        }

        return this.cancelButton;
    }

    public void initCancelButton(){
        this.cancelButton = new JButton("cancel");
    }


    public JButton getApplyButton(){

        if(this.applyButton == null){
            initApplyButton();
        }

        return this.applyButton;
    }

    public void initApplyButton(){
        this.applyButton = new JButton("apply");
    }



    public JFormattedTextField getFontSizeField(){

        if(this.fontSizeField == null){
            initFontSizeField();
        }

        return this.fontSizeField;
    }

    public void initFontSizeField(){
        this.fontSizeField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        this.fontSizeField.setValue(this.getController().getFontSize());

    }

    public JComboBox<String> getFontFamilyComboBox(){

        if(this.fontFamilyComboBox == null){
            initFontFamilyComboBox();
        }

        return this.fontFamilyComboBox;
    }

    public void initFontFamilyComboBox(){

        this.fontFamilyComboBox = new JComboBox<>(getFontNames());
        String currentFont = this.getController().getFontFamily();
        this.fontFamilyComboBox.setSelectedItem(currentFont);

    }

    public String[] getFontNames(){
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }


    public JComboBox<String> getThemeComboBox(){

        if(this.themeComboBox == null){
            initThemeComboBox();
        }

        return this.themeComboBox;
    }

    public void initThemeComboBox(){
        this.themeComboBox = new JComboBox<>(getLafNames());

    }

    public String[] getLafNames(){
        return this.getLafMap().keySet().toArray(new String[0]);
    }

    public HashMap<String, String> getLafMap(){

        if(this.lafMap == null){
            initLafMap();
        }

        return this.lafMap;
    }

    public void initLafMap(){
        this.lafMap = new HashMap<>();

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

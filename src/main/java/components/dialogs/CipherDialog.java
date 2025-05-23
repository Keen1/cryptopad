package components.dialogs;

import controllers.KeyStoreController;
import controllers.MainPanelController;
import models.FileModel;
import util.enums.CipherAlgorithms;
import util.enums.CipherBlockModes;
import util.enums.CipherBlockSizes;
import util.enums.CipherPaddingModes;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.*;
import java.awt.*;
import java.security.SecureRandom;
import java.util.List;

public class CipherDialog extends JDialog {
    private JComboBox<String> cipherComboBox;
    private JComboBox<String> blockModeComboBox;
    private JComboBox<String> paddingComboBox;
    private JComboBox<Integer> keyLengthComboBox;
    private JComboBox<String> tabTitles;

    private JButton generateKeyButton;
    private JButton applyButton;
    private JButton okayButton;
    private JButton cancelButton;

    private final KeyStoreController keyStoreController;
    private final MainPanelController mainPanelController;

    private SecretKey generatedKey;

    public CipherDialog(KeyStoreController keyStoreController, MainPanelController mainPanelController){
        super((JFrame)null, "Cipher Selection", true);
        this.mainPanelController = mainPanelController;
        this.keyStoreController = keyStoreController;
        initComponents();

    }

    public MainPanelController getMainPanelController(){
        return this.mainPanelController;
    }
    public KeyStoreController getKeyStoreController(){
        return this.keyStoreController;
    }



    public void initTabTitles(){
        List<String> titles = this.getMainPanelController().getTitles();
        this.tabTitles = new JComboBox<>();
        for(String title : titles){
            this.tabTitles.addItem(title);
        }

    }

    public void setSelectedTabTitle(String title){
        this.getTabTitles().setSelectedItem(title);
    }
    public JComboBox<String> getTabTitles(){
        if(this.tabTitles == null){
            initTabTitles();
        }
        return this.tabTitles;
    }

    public void setGeneratedKey(SecretKey generatedKey){
        this.generatedKey = generatedKey;
    }

    public SecretKey getGeneratedKey(){
        return this.generatedKey;
    }


    public void initComponents(){

        JPanel selectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        selectionPanel.add(new JLabel("cipher:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getCipherComboBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        selectionPanel.add(new JLabel("block mode:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getBlockModeComboBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        selectionPanel.add(new JLabel("key length:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getKeyLengthComboBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        selectionPanel.add(new JLabel("padding:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getPaddingComboBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        selectionPanel.add(new JLabel("file:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(getTabTitles(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        selectionPanel.add(getGenerateKeyButton(), gbc);

        setLayout(new BorderLayout());
        add(selectionPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.add(getApplyButton());
        buttonPanel.add(getOkayButton());
        buttonPanel.add(getCancelButton());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);





    }

    private void initOkayButton(){
        this.okayButton = new JButton("okay");
        this.okayButton.addActionListener(event -> dispose());
        this.okayButton.setEnabled(false);
    }
    public JButton getOkayButton(){
        if(this.okayButton == null){
            initOkayButton();
        }
        return this.okayButton;
    }

    public void initCancelButton(){
        this.cancelButton = new JButton("cancel");
        this.cancelButton.addActionListener(event -> {
            int choice = JOptionPane.showConfirmDialog(this, "any key generated will be deleted. Are you sure?", "cancel", JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                if(this.getGeneratedKey() != null){
                    String alias = (String)this.getTabTitles().getSelectedItem();
                    this.getKeyStoreController().removeKey(alias);
                    this.setGeneratedKey(null);
                }
                dispose();
            }
            dispose();
        });
    }

    public JButton getCancelButton(){
        if(this.cancelButton == null){
            initCancelButton();
        }
        return this.cancelButton;
    }

    public void initApplyButton(){
        this.applyButton = new JButton("apply");
        this.applyButton.setEnabled(false);
        this.applyButton.addActionListener(event ->{
            String alias = (String)this.getTabTitles().getSelectedItem();
            boolean accepted = this.applyKey(alias);
            if(accepted){
                this.applyCipherToModel();
                this.getOkayButton().setEnabled(true);
                this.getApplyButton().setEnabled(false);

            }

        });
    }

    public JButton getApplyButton(){
        if(this.applyButton == null){
            initApplyButton();
        }
        return this.applyButton;
    }

    public void initGenerateKeyButton(){
        this.generateKeyButton = new JButton("generate key for current file");
        this.generateKeyButton.addActionListener(event -> {
            initKey();
            this.getApplyButton().setEnabled(true);
        });
    }

    public JButton getGenerateKeyButton(){
        if(this.generateKeyButton == null){
            initGenerateKeyButton();
        }
        return this.generateKeyButton;
    }


    public void initPaddingComboBox(){
        this.paddingComboBox = new JComboBox<>(CipherPaddingModes.getNames());
    }
    public JComboBox<String> getPaddingComboBox(){
        if(this.paddingComboBox == null){
            initPaddingComboBox();
        }
        return this.paddingComboBox;
    }

    public void initKeyLengthComboBox(){
        this.keyLengthComboBox = new JComboBox<>();
    }

    public JComboBox<Integer> getKeyLengthComboBox(){
        if(this.keyLengthComboBox == null){
            initKeyLengthComboBox();
        }
        return this.keyLengthComboBox;
    }
    public void initBlockModeComboBox(){
        this.blockModeComboBox = new JComboBox<>(CipherBlockModes.getNames());
    }
    public JComboBox<String> getBlockModeComboBox(){
        if(this.blockModeComboBox == null){
            initBlockModeComboBox();
        }
        return this.blockModeComboBox;
    }

    public void initCipherComboBox(){
        this.cipherComboBox = new JComboBox<>(CipherAlgorithms.getNames());
        updateKeyLengths();
        this.cipherComboBox.addActionListener(event -> updateKeyLengths());
    }
    public JComboBox<String> getCipherComboBox(){
        if(this.cipherComboBox == null){
            initCipherComboBox();
        }
        return this.cipherComboBox;
    }

    private void updateKeyLengths(){
        String cipher = (String)this.getCipherComboBox().getSelectedItem();
        this.getKeyLengthComboBox().removeAllItems();
        List<Integer> keyLengths = CipherAlgorithms.fromString(cipher).getSupportedKeyLengths();
        for(Integer kl : keyLengths){
            this.getKeyLengthComboBox().addItem(kl);
        }
    }

    private void initKey(){

        String cipher = (String) this.getCipherComboBox().getSelectedItem();
        int keyLength = (Integer)this.getKeyLengthComboBox().getSelectedItem();
        this.setGeneratedKey(this.getKeyStoreController().generateKey(cipher, keyLength));




    }

    private void storeKey(String alias){

        if(this.getGeneratedKey() != null){

            this.getKeyStoreController().storeKey(this.getGeneratedKey(), alias);
            this.getMainPanelController().updateStatus(String.format("Key stored for %s", alias));
            this.setGeneratedKey(null);

        }
    }

    private boolean applyKey(String title){

        if(this.getKeyStoreController().keyExists(title)){

            String message = "<html><div style='text-align: center'>A key already exists for this file.<br> Do you want to overwrite the previous key with the" +
                    " new one?<br></div></html>";
            String dialogTitle = String.format("key found for %s", title);
            int choice = JOptionPane.showConfirmDialog(this, message, dialogTitle, JOptionPane.YES_NO_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                this.storeKey(title);
                this.applyCipherToModel();
                this.getOkayButton().setEnabled(true);
                return true;

            }else{
                this.setGeneratedKey(null);
                return false;
            }
        }else{
            this.storeKey(title);
            this.getMainPanelController().updateStatus(String.format("key stored for %s\n", title));
            return true;
        }

    }

    private void applyCipherToModel(){
        String cipher = (String)this.getCipherComboBox().getSelectedItem();
        String blockMode = (String)this.getBlockModeComboBox().getSelectedItem();
        String padding = (String)this.getPaddingComboBox().getSelectedItem();
        String title = (String)this.getTabTitles().getSelectedItem();

        byte[] iv = new byte[CipherBlockSizes.fromString(cipher).getIvSize()];
        SecureRandom secRand = new SecureRandom();
        secRand.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        FileModel model = this.getMainPanelController().getFileModelForTab(title);
        model.setCipherAlgorithm(cipher);
        model.setBlockMode(blockMode);
        model.setPadding(padding);
        model.setIV(ivSpec);
    }








}

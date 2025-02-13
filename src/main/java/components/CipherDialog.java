package components;

import controllers.MainPanelController;
import controllers.SecretKeyController;
import util.enums.CipherAlgorithms;
import util.enums.CipherBlockModes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
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

    private SecretKeyController secretKeyController;
    private MainPanelController mainPanelController;

    private SecretKey generatedKey;

    public CipherDialog(SecretKeyController secretKeyController, MainPanelController mainPanelController){
        super((JFrame)null, "Cipher Selection", true);
        this.mainPanelController = mainPanelController;
        this.secretKeyController = secretKeyController;
        initComponents();
    }

    public MainPanelController getMainPanelController(){
        return this.mainPanelController;
    }
    public SecretKeyController getSecretKeyController(){
        return this.secretKeyController;
    }



    public void initTabTitles(){
        List<String> titles = this.getMainPanelController().getTitles();
        this.tabTitles = new JComboBox<>();
        for(String title : titles){
            this.tabTitles.addItem(title);
        }

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
            this.getOkayButton().setEnabled(true);
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
        this.paddingComboBox = new JComboBox<>(CipherBlockModes.getNames());
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
        this.setGeneratedKey(this.getSecretKeyController().generateKey(cipher, keyLength));

    }

    private boolean storeKey(String alias){
        if(this.getGeneratedKey() == null){
            return false;
        }else{
            try{
                this.getSecretKeyController().storeKey(this.getGeneratedKey(), alias);
                return true;

            }catch(KeyStoreException e){
                System.out.printf("error storing key forL %s\n error: %s\n", alias, e.getMessage());
                return false;
            }finally{
                this.setGeneratedKey(null);
            }

        }
    }








}

package components;

import controllers.SecretKeyController;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.List;

public class CipherDialog extends JDialog {
    private JComboBox<String> cipherComboBox;
    private JComboBox<String> blockModeComboBox;
    private JComboBox<String> paddingComboBox;
    private JComboBox<Integer> keyLengthComboBox;

    private JButton generateKeyButton;
    private JButton applyButton;
    private JButton cancelButton;

    private static final String[] BLOCK_MODES = {"CBC", "CFB", "CTR", "ECB", "GCM", "OFB"};
    private static final String[] PADDING_MODES = {"PKCS7Padding", "NoPadding"};
    private static final String[] CIPHERS = {"AES", "ARIA", "BLOWFISH", "CAMELLIA", "CAST5",
                                                "CAST6", "DSTU7624", "GOST3412-2015", "IDEA",
                                                "NOEKEON", "SERPENT", "SM4", "THREEFISH-1024",
                                                "TWOFISH", "XTEA"};
    private static final Map<String, List<Integer>> SUPPORTED_KEY_LENGTHS = new HashMap<>();

    static{
        SUPPORTED_KEY_LENGTHS.put("AES", Arrays.asList(128, 192, 256));
        SUPPORTED_KEY_LENGTHS.put("ARIA", Arrays.asList(128, 192, 256));
        SUPPORTED_KEY_LENGTHS.put("BLOWFISH", Arrays.asList(32, 64, 128, 256, 448));
        SUPPORTED_KEY_LENGTHS.put("CAMELLIA", Arrays.asList(128, 192, 256));
        SUPPORTED_KEY_LENGTHS.put("CAST5", Arrays.asList(40, 64, 80, 96, 112, 128));
        SUPPORTED_KEY_LENGTHS.put("CAST6", Arrays.asList(128, 160, 192, 224, 256));
        SUPPORTED_KEY_LENGTHS.put("DSTU7624", Arrays.asList(128, 256, 512));
        SUPPORTED_KEY_LENGTHS.put("GOST3412-2015", List.of(256));
        SUPPORTED_KEY_LENGTHS.put("IDEA", List.of(128));
        SUPPORTED_KEY_LENGTHS.put("NOEKEON", List.of(128));
        SUPPORTED_KEY_LENGTHS.put("SERPENT", Arrays.asList(128, 192, 256));
        SUPPORTED_KEY_LENGTHS.put("SM4", List.of(128));
        SUPPORTED_KEY_LENGTHS.put("THREEFISH-1024", List.of(1024));
        SUPPORTED_KEY_LENGTHS.put("TWOFISH", Arrays.asList(128, 192, 256));
        SUPPORTED_KEY_LENGTHS.put("XTEA", List.of(128));
    }
    private SecretKeyController controller;

    public CipherDialog(SecretKeyController controller){
        super((JFrame)null, "Cipher Selection", true);
        this.controller = controller;
        initComponents();
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

    public void initCancelButton(){
        this.cancelButton = new JButton("cancel");
    }

    public JButton getCancelButton(){
        if(this.cancelButton == null){
            initCancelButton();
        }
        return this.cancelButton;
    }

    public void initApplyButton(){
        this.applyButton = new JButton("apply");
    }

    public JButton getApplyButton(){
        if(this.applyButton == null){
            initApplyButton();
        }
        return this.applyButton;
    }

    public void initGenerateKeyButton(){
        this.generateKeyButton = new JButton("generate key for current file");
        this.generateKeyButton.addActionListener(event ->generateKey());
    }

    public JButton getGenerateKeyButton(){
        if(this.generateKeyButton == null){
            initGenerateKeyButton();
        }
        return this.generateKeyButton;
    }


    public void initPaddingComboBox(){
        this.paddingComboBox = new JComboBox<>(PADDING_MODES);
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
        this.blockModeComboBox = new JComboBox<>(BLOCK_MODES);
    }
    public JComboBox<String> getBlockModeComboBox(){
        if(this.blockModeComboBox == null){
            initBlockModeComboBox();
        }
        return this.blockModeComboBox;
    }

    public void initCipherComboBox(){
        this.cipherComboBox = new JComboBox<>(CIPHERS);
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
        List<Integer> keyLengths = SUPPORTED_KEY_LENGTHS.get(cipher);
        for(Integer kl : keyLengths){
            this.getKeyLengthComboBox().addItem(kl);
        }
    }

    private void generateKey(){
        String cipher = (String) this.getCipherComboBox().getSelectedItem();
        int keyLength = (Integer)this.getKeyLengthComboBox().getSelectedItem();
        System.out.printf("Cipher selected: %s\n Key length selected: %d", cipher, keyLength);

        try{
            KeyGenerator keyGen = KeyGenerator.getInstance(cipher);
            keyGen.init(keyLength);
            SecretKey key = keyGen.generateKey();
            byte[] keyBytes = key.getEncoded();
            String encodedKey = Base64.getEncoder().encodeToString(keyBytes);
            System.out.printf("Key generated successfully.\n Algorithm: %s\n length: %d\n value: %s", key.getAlgorithm(), keyLength, encodedKey);
            this.controller.storeKey(key, "test");

        } catch (NoSuchAlgorithmException e) {
            System.out.printf("No algorithm named %s: %s", cipher, e.getMessage());
        }
    }





}

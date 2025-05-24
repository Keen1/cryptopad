package components;


import components.dialogs.CipherDialog;
import components.panels.MainPanel;
import controllers.KeyStoreController;
import controllers.MainPanelController;
import models.KeyStoreModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class CipherDialogTest {
    private static CipherDialog dialog;

    @BeforeAll
    public static void setup(){

        KeyStoreController keyStoreController = new KeyStoreController();
        KeyStoreModel model = new KeyStoreModel();
        MainPanel panel = new MainPanel(model);
        MainPanelController mainPanelController = new MainPanelController(panel);
        dialog = new CipherDialog(keyStoreController, mainPanelController);

    }

    //test that generating a key generates a non-null value for the generated key attribute of the dialog
    @Test
    public void generateKeyTest(){

    }

    //test that the generated key is successfully stored in the keystore
    @Test
    public void applyKeyTest(){

    }
}

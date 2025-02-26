package components;

import controllers.KeyStoreSetupController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.text.Document;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeyStoreSetupPanelTest {

    private KeyStoreSetupPanel panel;

    @Mock
    private KeyStoreSetupController mockController;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        panel = new KeyStoreSetupPanel();

        Field controllerField = KeyStoreSetupPanel.class.getDeclaredField("controller");
        controllerField.setAccessible(true);
        controllerField.set(panel, mockController);

    }

    @Test
    void testInitialComponentsCreation(){
        assertNotNull(panel.getPasswordField(), "password field should not be null.");
        assertNotNull(panel.getConfirmField(), "confirm password field should not be null.");
        assertNotNull(panel.getMessageLabel(), "message label should not be null.");
        assertNotNull(panel.getPwReqFeedbackLabel(), "feedback label should not be null.");
        assertNotNull(panel.getConfirmBtn(), "confirm button should not be null.");
    }

    @Test
    void testInitialComponentStates(){
        assertEquals(0, panel.getPasswordField().getPassword().length, "Password field should have no entry on init.");
        assertEquals(0, panel.getConfirmField().getPassword().length, "confirm field should have no entry on init.");
        assertEquals("", panel.getMessageLabel().getText(), "message label should be empty on init.");
        assertEquals("confirm", panel.getConfirmBtn().getText(), "confirm button text should be confirm on init");

    }

    @Test
    void testGetController(){

        KeyStoreSetupPanel realPanel = new KeyStoreSetupPanel();
        assertNotNull(realPanel.getController(), "Controller should be created on init of panel.");
        assertInstanceOf(KeyStoreSetupController.class, realPanel.getController(), "controller should be instance of KeyStoreSetupController");

    }

    @Test
    void testConfirmButtonActionRequirementsMet(){

        //configure mocks
        when(mockController.areRequirementsMet()).thenReturn(true);

        //set a test password
        String testPw = "TestPassword123!";
        panel.getPasswordField().setText(testPw);
        panel.getConfirmBtn().doClick();

        //verify appropriate methods called
        verify(mockController).areRequirementsMet();
        verify(mockController).setPassword(any(char[].class));
        verify(mockController).createKeyStore();


    }

    @Test
    void testConfirmButtonActionRequirementsNotMet(){

        when(mockController.areRequirementsMet()).thenReturn(false);

        panel.getConfirmBtn().doClick();

        verify(mockController).areRequirementsMet();
        verify(mockController, never()).setPassword(any(char[].class));
        verify(mockController, never()).createKeyStore();

    }

    @Test
    void testPasswordFieldDocListeners() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

        Document passwordDoc = panel.getPasswordField().getDocument();
        Document confirmDoc = panel.getConfirmField().getDocument();

        Field listenerListField = Class.forName("javax.swing.text.AbstractDocument").getDeclaredField("listenerList");
        listenerListField.setAccessible(true);

        EventListenerList passwordFieldListeners = (EventListenerList) listenerListField.get(passwordDoc);
        EventListenerList confirmFieldListeners = (EventListenerList) listenerListField.get(confirmDoc);

        DocumentListener[] passwordDocListeners = passwordFieldListeners.getListeners(DocumentListener.class);
        DocumentListener[] confirmDocListeners = confirmFieldListeners.getListeners(DocumentListener.class);

        assertEquals(2, passwordDocListeners.length, "Password field should have 2 document listeners.");
        assertEquals(1, confirmDocListeners.length, "Confirm field should have 1 document listener.");



    }






}

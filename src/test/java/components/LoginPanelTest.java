package components;

import controllers.LoginController;
import models.KeyStoreResultModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class LoginPanelTest {

    private LoginPanel panel;

    @Mock
    private LoginController mockController;

    @Mock
    Consumer<KeyStoreResultModel> mockOnLoginSuccess;

    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException{

        panel = new LoginPanel(mockOnLoginSuccess);
        Field controllerField = LoginPanel.class.getDeclaredField("controller");
        controllerField.setAccessible(true);
        controllerField.set(panel, mockController);



    }

    @Test
    void testInitialComponentsCreation(){

        assertNotNull(panel.getLoginLabel(), "login label should not be null.");
        assertNotNull(panel.getPasswordField(), "password field should not be null");
        assertNotNull(panel.getSubmitButton(), "submit button should not be null");
        assertNotNull(panel.getMessageLabel(), "message label should not be null");

    }









}

package listeners.menu;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface MenuItemHandler extends ActionListener {

    @Override
    void actionPerformed(ActionEvent e);

}

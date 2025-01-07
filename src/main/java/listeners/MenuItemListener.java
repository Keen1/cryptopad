package listeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public interface MenuItemListener extends ActionListener {
    @Override
    void actionPerformed(ActionEvent e);

}

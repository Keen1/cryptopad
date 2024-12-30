import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gui {
    public Gui(){
        initComponents();
    }

    private JToolBar initToolbar(){
        JToolBar toolbar = new JToolBar();
        List<JButton> buttons = initToolbarButtons();
        for(JButton button : buttons){
            toolbar.add(button);
        }
        return toolbar;

    }

    private List<JButton> initToolbarButtons(){
        ArrayList<JButton> buttons = new ArrayList<>();
        JButton settings = new JButton("settings");
        buttons.add(settings);
        return buttons;

    }

    private JPanel initTabbedPane(){
        JPanel panel = new JPanel(new BorderLayout());
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.add("untitled", initUntitledInitPanel());
        panel.add(tabPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel initUntitledInitPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JFrame initFrame(){
        JFrame frame = new JFrame("cryptopad");
        frame.setPreferredSize(new Dimension(800, 1000));
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private void initComponents(){
        JFrame frame = initFrame();
        JToolBar toolbar = initToolbar();
        frame.add(toolbar, BorderLayout.NORTH);
        JPanel tabPanel = initTabbedPane();
        frame.add(tabPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


}

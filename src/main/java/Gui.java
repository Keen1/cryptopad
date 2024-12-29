import javax.swing.*;
import java.awt.*;

public class Gui {
    private JFrame frame;
    private JTextArea mainTextArea;
    private JTabbedPane tabPane;
    private JToolBar toolbar;

    public Gui(){
        initComponents();
    }

    public JTextArea initMainTextArea(){

        this.mainTextArea = new JTextArea();
        Dimension frameDimension = this.getFrameDimension();
        this.mainTextArea.setPreferredSize(this.setTextAreaDimension(frameDimension));


        return this.mainTextArea;
    }

    public Dimension setTextAreaDimension(Dimension frameDimension){

        int textAreaHeight = frameDimension.height - 50;
        int textAreaWidth = frameDimension.width - 50;

        return new Dimension(textAreaWidth, textAreaHeight);

    }

    public JTextArea getMainTextArea(){

        if(this.mainTextArea == null){
            return this.initMainTextArea();
        }
        return this.mainTextArea;
    }

    public JTabbedPane initTabPane(){

        this.tabPane = new JTabbedPane();
        return this.tabPane;
    }

    public JTabbedPane getTabPane(){

        if(this.tabPane == null){
            return this.initTabPane();
        }
        return this.tabPane;
    }

    public JToolBar initToolBar(){

        this.toolbar = new JToolBar();
        return this.toolbar;

    }

    public JToolBar getToolBar(){

        if(this.toolbar == null){
            return this.initToolBar();
        }

        return this.toolbar;
    }


    public JFrame initFrame(){

        this.frame = new JFrame("cryptopad");
        this.frame.setPreferredSize(new Dimension(800, 1000));
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return this.frame;
    }

    public JFrame getFrame(){

        if(this.frame == null){
            return this.initFrame();
        }

        return this.frame;
    }

    public Dimension getFrameDimension(){

        return this.getFrame().getPreferredSize();

    }

    public void showFrame(){

        this.frame.setVisible(true);

    }

    public void initComponents(){

        JFrame frame = initFrame();
        JTextArea mainText = getMainTextArea();
        frame.add(mainText);
        frame.pack();
        showFrame();
    }



}

package components;

import javax.swing.*;
import java.awt.*;

//TODO: finish implementation and refactor gui using this component.
public class StatusLabel extends JPanel {
    private JLabel messageLabel;
    private JProgressBar progressBar;
    private Timer clearTimer;
    private Timer progressTimer;

    private static final int MESSAGE_DURATION = 3000;
    private static final int PROGRESS_UPDATE_FREQ = 50;

    public StatusLabel(){
        setLayout(new BorderLayout());
        initMessageLabel();
    }

    private void initMessageLabel(){

        this.messageLabel = new JLabel("");

    }

    private void addMessageLabel(){

        if(this.messageLabel == null){
            initMessageLabel();
        }

        add(this.messageLabel, BorderLayout.WEST);
    }

    private void initProgressBar(){
        this.progressBar = new JProgressBar(0, MESSAGE_DURATION);
        this.progressBar.setPreferredSize(new Dimension(this.progressBar.getPreferredSize().width, 5));
    }

    private void addProgressBar(){
        if(this.progressBar == null){
            initProgressBar();
        }

        /*
        * TODO: Need to check this border layout position and make sure its the best place for the progress bar.
        *  The actual status text is set in the WEST region so this might be best set in the CENTER.
        */
        add(this.progressBar, BorderLayout.SOUTH);

    }

    private void initClearTimer(){
        this.clearTimer = new Timer(MESSAGE_DURATION, e ->{
           this.messageLabel.setText("");
           this.progressBar.setValue(0);
           this.progressBar.setVisible(false);
        });
        this.clearTimer.setRepeats(false);
    }

    //TODO: Implement logic for the progress timer.



}

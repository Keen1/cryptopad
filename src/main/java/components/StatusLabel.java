package components;

import javax.swing.*;
import java.awt.*;

/*
* TODO want to reverse the count of the progress bar - the bar should start filled and defill until it reaches the end -
*  then disappear. This behavior is more in line with what the progress is displaying - the amount of time left before
*  the update text disappears.
*/

public class StatusLabel extends JPanel {
    private JLabel messageLabel;
    private JProgressBar progressBar;
    private Timer clearTimer;
    private Timer progressTimer;

    private int timeElapsed;


    private static final int MESSAGE_DURATION = 3000;
    private static final int PROGRESS_UPDATE_FREQ = 50;

    public StatusLabel(){
        this.initLayout();
        this.initComponents();
        this.initTimers();
    }

    private void initLayout(){
        setLayout(new BorderLayout());
    }

    private void initComponents(){
        this.addMessageLabel();
        this.addProgressBar();
        this.hideProgressBar();
    }
    private void initTimers(){
        this.initClearTimer();
        this.initProgressTimer();
    }

    private int getTimeElapsed(){
        return this.timeElapsed;
    }
    private void setTimeElapsed(int timeElapsed){
        this.timeElapsed = timeElapsed;
    }
    private void resetTimeElapsed(){
        this.setTimeElapsed(0);
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
        //TODO investigate how to reduce size of the progress bar - it currently runs the length of the component in the frame
        this.progressBar.setPreferredSize(new Dimension(50, 5));

    }
    private void addProgressBar(){
        if(this.progressBar == null){
            initProgressBar();
        }
        add(this.progressBar, BorderLayout.CENTER);
    }
    private void hideProgressBar(){
        this.progressBar.setVisible(false);
    }
    private void showProgressBar(){
        this.progressBar.setVisible(true);
    }
    private void resetProgressBar(){
        this.progressBar.setValue(0);
    }
    private void initClearTimer(){
        this.clearTimer = new Timer(MESSAGE_DURATION, e -> handleClearTimerEvent());
    }
    private void handleClearTimerEvent(){
        this.clearMessage();
        this.resetProgressBar();
        this.hideProgressBar();
    }

    private void initProgressTimer(){
        this.progressTimer = new Timer(PROGRESS_UPDATE_FREQ, e -> handleProgressTimerEvent());
    }
    private void handleProgressTimerEvent(){
        this.updateProgress();
        this.checkAndStopProgress();
    }
    private void updateProgress(){
        int elapsed = this.getTimeElapsed();
        this.setTimeElapsed(elapsed + PROGRESS_UPDATE_FREQ);
        this.progressBar.setValue(this.getTimeElapsed());
    }
    private void checkAndStopProgress(){
        if(this.getTimeElapsed() >= MESSAGE_DURATION){
            this.progressTimer.stop();
        }
    }
    private void clearMessage(){
        this.messageLabel.setText("");
    }
    private void startTimers(){
        this.clearTimer.restart();
        this.progressTimer.restart();
    }
    public void setStatusText(String message){
        this.messageLabel.setText(message);
        this.resetProgressBar();
        this.showProgressBar();
        this.resetTimeElapsed();
        this.startTimers();
    }




}

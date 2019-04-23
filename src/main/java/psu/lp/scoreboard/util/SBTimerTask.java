package psu.lp.scoreboard.util;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class SBTimerTask extends TimerTask {

    private int minute;
    private int second;
    private Label timerLabel;

    public SBTimerTask(int minute, int second, Label timerLabel) {
            this.minute = minute;
            this.second = second;
            this.timerLabel = timerLabel;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (second > 0) {
                second--;
            } else {
                second = 59;
                minute--;
            }
            if (minute == 0 && second == 0) {
                return;
            }
            timerLabel.setText(String.format("%02d", minute) + ":" + String.format("%02d", second));
        });
    }
}

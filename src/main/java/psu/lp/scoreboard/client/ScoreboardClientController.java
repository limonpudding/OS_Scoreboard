package psu.lp.scoreboard.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.SBTimerTask;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.util.Timer;

public class ScoreboardClientController {

    private Timer timer;
    private int half = 1;
    private int timerSecond;
    private int timerMinute;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label teamNamesLabel;

    @FXML
    private Label team1ScoreLabel;

    @FXML
    private Label team2ScoreLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label halfLabel;

    public ScoreboardClientController() {
        ActionListener.getInstance().setController(this);
    }

    public void setTeamNames(ScoreboardAction action) {
        System.out.println(teamNamesLabel.getText());
        Platform.runLater(() -> {
            teamNamesLabel.setText(action.getStringValue1() + " VS " + action.getStringValue2());
        });
    }

    public void setScore(ScoreboardAction action) {
        Platform.runLater(() -> {
            team1ScoreLabel.setText(String.valueOf(action.getIntValue1()));
            team2ScoreLabel.setText(String.valueOf(action.getIntValue2()));
        });
    }

    public void setTime(ScoreboardAction action) {
        Platform.runLater(() -> {
            this.timerMinute = action.getIntValue1();
            this.timerSecond = action.getIntValue2();
            startTimer();
        });
    }

    public void stopTime() {
        Platform.runLater(this::pauseTimer);
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new SBTimerTask(timerMinute, timerSecond, timerLabel), 0, 1000);
    }

    private void pauseTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void setHalf(ScoreboardAction action) {
        Platform.runLater(() -> {
            pauseTimer();
            timerLabel.setText("45:00");
            halfLabel.setText("Тайм: " + action.getIntValue1());
        });
    }

    public void resetHalfAndTime() {
        Platform.runLater(() -> {
            pauseTimer();
            timerLabel.setText("45:00");
            halfLabel.setText("Тайм: 1");
        });
    }

}

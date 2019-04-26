package psu.lp.scoreboard.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.SBTimerTask;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.io.IOException;
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

    @FXML
    public Label goal1Label;

    @FXML
    public Label goal2Label;

    @FXML
    public Label goal3Label;

    @FXML
    public Label goal4Label;

    public ScoreboardClientController() throws IOException {
        ActionListener.getInstance().setController(this);
        ActionListener.getInstance().getAllInfo();
    }

    void setTeamNames(ScoreboardAction action) {
        Platform.runLater(() -> {
            teamNamesLabel.setText(action.getStringValue1() + " VS " + action.getStringValue2());
        });
    }

    void setScore(ScoreboardAction action) {
        Platform.runLater(() -> {
            team1ScoreLabel.setText(String.valueOf(action.getIntValue1()));
            team2ScoreLabel.setText(String.valueOf(action.getIntValue2()));
            if (action.getStringValue1() != null) {
                updateGoalsList(action.getStringValue1());
            }
        });
    }

    void setTime(ScoreboardAction action) {
        Platform.runLater(() -> {
            this.timerMinute = action.getIntValue1();
            this.timerSecond = action.getIntValue2();
            startTimer();
        });
    }

    void stopTime(ScoreboardAction action) {
        Platform.runLater(() -> pauseTimerAndSetTimerLabel(action));
    }

    private synchronized void startTimer() {
        Platform.runLater(() -> {
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            timer.schedule(new SBTimerTask(timerMinute, timerSecond, timerLabel), 0, 1000);
        });
    }

    private void pauseTimerAndSetTimerLabel(ScoreboardAction action) {
        Platform.runLater(() -> {
            pauseTimer();
            timerLabel.setText(action.getStringValue1());
        });
    }

    private void pauseTimer() {
        Platform.runLater(() -> {
            if (timer != null) {
                timer.cancel();
            }
        });
    }

    void setHalf(ScoreboardAction action) {
        Platform.runLater(() -> {
            pauseTimer();
            timerLabel.setText("45:00");
            halfLabel.setText("Тайм: " + action.getIntValue1());
        });
    }

    void resetHalfAndTime() {
        Platform.runLater(() -> {
            pauseTimer();
            timerLabel.setText("45:00");
            halfLabel.setText("Тайм: 1");
        });
    }

    private void updateGoalsList(String goalInfo) {
        goal4Label.setText(goal3Label.getText());
        goal3Label.setText(goal2Label.getText());
        goal2Label.setText(goal1Label.getText());
        goal1Label.setText(goalInfo);
    }
}

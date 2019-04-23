package psu.lp.scoreboard.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.ScoreboardAction;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ScoreboardClientController {

    private Timer timer;
    private ClientTimerTask task;
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

    public ScoreboardClientController() throws IOException, InterruptedException {
        timer = new Timer();
        ActionListener.getInstance().setController(this);
        ActionListener.getInstance().getAllInfo();
    }

    public void setTeamNames(ScoreboardAction action) {
        Platform.runLater(() -> {
            teamNamesLabel.setText(action.getStringValue1() + " VS " + action.getStringValue2());
        });
    }

    public void setScore(ScoreboardAction action) {
        Platform.runLater(() -> {
            team1ScoreLabel.setText(String.valueOf(action.getIntValue1()));
            team2ScoreLabel.setText(String.valueOf(action.getIntValue2()));
            updateGoalsList(action.getStringValue1());
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

    private synchronized void startTimer() {
        Platform.runLater(() -> {
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            task = new ClientTimerTask();
            timer.schedule(task, 0, 1000);
        });
    }

    private void pauseTimer() {
        Platform.runLater(() -> {
            if (timer != null) {
                timer.cancel();
            }
        });
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

    private void updateGoalsList(String goalInfo) {
        goal4Label.setText(goal3Label.getText());
        goal3Label.setText(goal2Label.getText());
        goal2Label.setText(goal1Label.getText());
        goal1Label.setText(goalInfo);
    }

    public class ClientTimerTask extends TimerTask {



        public ClientTimerTask() {
        }
        @Override
        public void run() {
            Platform.runLater(() -> {
                if (timerSecond > 0) {
                    timerSecond--;
                } else {
                    timerSecond = 59;
                    timerMinute--;
                }
                if (timerMinute == 0 && timerSecond == 0) {
                    return;
                }
                timerLabel.setText(String.format("%02d", timerMinute) + ":" + String.format("%02d", timerSecond));
            });
        }
    }
}

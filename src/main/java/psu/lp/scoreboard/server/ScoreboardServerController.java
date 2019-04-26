package psu.lp.scoreboard.server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.SBTimerTask;
import psu.lp.scoreboard.util.ScoreboardAction;
import psu.lp.scoreboard.util.ScoreboardActionType;

import java.io.IOException;
import java.util.Timer;

public class ScoreboardServerController {

    private int score1;
    private int score2;

    private Timer timer;
    private int half;
    private int timerSecond;
    private int timerMinute;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField team1TextField;

    @FXML
    private TextField team2TextField;

    @FXML
    private Button selectTeamNamesButton;

    @FXML
    private Button encreaseTeam1ScoreButton;

    @FXML
    private Button encreaseTeam2ScoreButton;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private Label halfLabel;

    @FXML
    private Button startTimerButton;

    @FXML
    private Button pauseTimerButton;

    @FXML
    private Button nextHalfButtonl;

    @FXML
    private Button clearHalfButton;

    @FXML
    public Label goal1Label;

    @FXML
    public Label goal2Label;

    @FXML
    public Label goal3Label;

    @FXML
    public Label goal4Label;

    public ScoreboardServerController() {
        score1 = 0;
        score2 = 0;
        half = 1;
        timerMinute = 45;
        timerSecond = 0;

        try {
            NewClientListener.getInstance().setController(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Не удалось подключить модуль работы с новыми клиентами!");
        }
    }

    public void selectTeamNames() {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.SET_TEAM_NAMES);
        action.setStringValue1(team1TextField.getText());
        action.setStringValue2(team2TextField.getText());
        ActionSender.getInstance().sendScoreboardAction(action);
    }

    public void encreaseTeam1Score() throws IOException {
        score1 += 1;
        updateScore(team1TextField.getText());
    }

    public void encreaseTeam2Score() throws IOException {
        score2 += 1;
        updateScore(team2TextField.getText());
    }

    private void updateScore(String team) throws IOException {
        setServerScore();
        NewGoal newGoal = new NewGoal();
        String goal = newGoal.display();
        String goalInfo = String.format("[%02d", timerMinute) + ":" + String.format("%02d", timerSecond) + "] " + team + ": " + goal;
        updateGoalsList(goalInfo);
        sendScore(goalInfo);
    }

    public void resetScore() {
        score1 = 0;
        score2 = 0;
        setServerScore();
        sendScore(null);
    }

    private void setServerScore() {
        scoreLabel.setText(score1 + " : " + score2);
    }

    private void sendScore(String goalInfo) {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.INCREASE_SCORE);
        action.setIntValue1(score1);
        action.setIntValue2(score2);
        if (goalInfo != null) {
            action.setStringValue1(goalInfo);
        }
        ActionSender.getInstance().sendScoreboardAction(action);
    }

    public void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new SBTimerTask(timerMinute, timerSecond, timerLabel), 0, 1000);
        sendStartTimer();
    }

    private void sendStartTimer() {
        saveTimeFromLabel();
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.TIMER_START);
        action.setIntValue1(timerMinute);
        action.setIntValue2(timerSecond);
        ActionSender.getInstance().sendScoreboardAction(action);
    }

    public void pauseTimer() {
        saveTimeFromLabel();
        timer.cancel();
        sendPauseTimer();
    }

    private void sendPauseTimer() {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.TIMER_PAUSE);
        action.setStringValue1(timerLabel.getText());
        ActionSender.getInstance().sendScoreboardAction(action);
    }

    public void newHalf() {
        resetTime();
        half += 1;
        halfLabel.setText("Тайм: " + half);
        sendHalf();
    }

    private void sendHalf() {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.NEW_HALF);
        action.setIntValue1(half);
        ActionSender.getInstance().sendScoreboardAction(action);
    }

    public void clearHalf() {
        half = 1;
        halfLabel.setText("Тайм: 1");
        resetTime();
        sendResetHalf();
    }

    private void sendResetHalf() {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.RESET_HALF);
        ActionSender.getInstance().sendScoreboardAction(action);
    }

    private void resetTime() {
        if (timer != null) {
            timer.cancel();
        }
        timerLabel.setText("45:00");
        timerMinute = 45;
        timerSecond = 0;
    }

    private void saveTimeFromLabel() {
        timerMinute = Integer.valueOf(timerLabel.getText().substring(0, 2));
        timerSecond = Integer.valueOf(timerLabel.getText().substring(3, 5));
    }

    synchronized void sendAll() {
        selectTeamNames();
        sendScore(null);
        sendScore(goal4Label.getText());
        sendScore(goal3Label.getText());
        sendScore(goal2Label.getText());
        sendScore(goal1Label.getText());
        sendHalf();
        if (timer != null) {
            sendStartTimer();
        } else {
            sendPauseTimer();
        }
    }

    private void updateGoalsList(String goalInfo) {
        goal4Label.setText(goal3Label.getText());
        goal3Label.setText(goal2Label.getText());
        goal2Label.setText(goal1Label.getText());
        goal1Label.setText(goalInfo);
    }
}

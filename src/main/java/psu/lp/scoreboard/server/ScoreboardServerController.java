package psu.lp.scoreboard.server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.SBTimerTask;
import psu.lp.scoreboard.util.ScoreboardAction;
import psu.lp.scoreboard.util.ScoreboardActionType;

import java.net.SocketException;
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

    public ScoreboardServerController() {
        score1 = 0;
        score2 = 0;
        half = 1;
        timerMinute = 45;
        timerSecond = 0;
        try {
            Thread thread = new Thread(new NewClientListener(this));
        } catch (SocketException e) {
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

    public void encreaseTeam1Score() {
        score1 += 1;
        setServerScore();
        sendScore();
    }

    public void encreaseTeam2Score() {
        score2 += 1;
        setServerScore();
        sendScore();
    }

    public void resetScore() {
        score1 = 0;
        score2 = 0;
        setServerScore();
        sendScore();
    }

    private void setServerScore() {
        scoreLabel.setText(score1 + " : " + score2);
    }

    private void sendScore() {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.INCREASE_SCORE);
        action.setIntValue1(score1);
        action.setIntValue2(score2);
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
        timer.cancel();
        timerLabel.setText("45:00");
        timerMinute = 45;
        timerSecond = 0;
    }

    private void saveTimeFromLabel() {
        timerMinute = Integer.valueOf(timerLabel.getText().substring(0, 2));
        timerSecond = Integer.valueOf(timerLabel.getText().substring(3, 5));
    }

    public void sendAll() {
        selectTeamNames();
        sendScore();
        if (timer != null) {
            sendStartTimer();
        } else {
            sendStartTimer();
            sendPauseTimer();
        }
        sendHalf();
    }
}

package psu.lp.scoreboard.server;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.ScoreboardAction;
import psu.lp.scoreboard.util.ScoreboardActionType;

public class ScoreboardServerController {

    private int score1 = 0;
    private int score2 = 0;

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

    public ScoreboardServerController() {

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
        sendScore();
    }

    public void encreaseTeam2Score() {
        score2 += 1;
        sendScore();
    }

    private void sendScore() {
        ScoreboardAction action = new ScoreboardAction();
        action.setActionType(ScoreboardActionType.INCREASE_SCORE);
        action.setIntValue1(score1);
        action.setIntValue2(score2);
        ActionSender.getInstance().sendScoreboardAction(action);
    }
}

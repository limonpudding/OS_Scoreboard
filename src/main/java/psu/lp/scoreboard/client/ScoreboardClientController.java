package psu.lp.scoreboard.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import psu.lp.scoreboard.util.ScoreboardAction;

public class ScoreboardClientController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label teamNamesLabel;

    @FXML
    private Label team1ScoreLabel;

    @FXML
    private Label team2ScoreLabel;

    public ScoreboardClientController() {
        ActionListener.getInstance().setController(this);
    }

    public synchronized void setTeamNames(ScoreboardAction action) {
        System.out.println(teamNamesLabel.getText());
        Platform.runLater(() -> {
            teamNamesLabel.setText(action.getStringValue1() + " VS " + action.getStringValue2());
        });
    }

    public synchronized void setScore(ScoreboardAction action) {
        Platform.runLater(() -> {
            team1ScoreLabel.setText(String.valueOf(action.getIntValue1()));
            team2ScoreLabel.setText(String.valueOf(action.getIntValue2()));
        });
    }
}

package psu.lp.scoreboard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Scoreboard extends Application {
    public static Stage scoreboardStage;

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        scoreboardStage = stage;
        Parent scoreboardForm = FXMLLoader.load(getClass().getResource("/layouts/ScoreboardTemplate.fxml"));
        scoreboardStage.setTitle("Табло");
        scoreboardStage.setScene(new Scene(scoreboardForm, 600, 400));
        scoreboardStage.setOnCloseRequest(event -> System.exit(0));
        scoreboardStage.setResizable(true);
        scoreboardStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package psu.lp.scoreboard;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScoreboardServer extends Application {
    public static Stage scoreboardServerStage;

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        scoreboardServerStage = stage;
        Parent scoreboardForm = FXMLLoader.load(getClass().getResource("/layouts/ScoreboardServerTemplate.fxml"));
        scoreboardServerStage.setTitle("Табло сервер");
        scoreboardServerStage.setScene(new Scene(scoreboardForm, 600, 400));
        scoreboardServerStage.setOnCloseRequest(event -> System.exit(0));
        scoreboardServerStage.setResizable(true);
        scoreboardServerStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

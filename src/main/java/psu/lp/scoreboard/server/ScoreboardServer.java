package psu.lp.scoreboard.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScoreboardServer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        Parent scoreboardForm = FXMLLoader.load(getClass().getResource("/layouts/ScoreboardServerTemplate.fxml"));
        stage.setTitle("Табло сервер");
        stage.setScene(new Scene(scoreboardForm, 600, 400));
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

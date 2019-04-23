package psu.lp.scoreboard.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.SocketException;

public class ScoreboardClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Platform.setImplicitExit(false);
        Parent scoreboardForm = FXMLLoader.load(getClass().getResource("/layouts/ScoreboardClientTemplate.fxml"));
        stage.setTitle("Табло");
        stage.setScene(new Scene(scoreboardForm, 600, 338));
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setResizable(true);
        stage.show();
    }


    public static void main(String[] args) throws SocketException {
        Thread actionListener = new Thread(ActionListener.getInstance());
        actionListener.start();
        launch(args);
    }
}

package psu.lp.scoreboard.server;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class NewGoal {
    private String name;

    public String display() throws IOException {
        Stage window = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/NewGoalTemplate.fxml"));
        Parent newGoalForm = loader.load();
        window.setTitle("Гол!");
        window.setScene(new Scene(newGoalForm, 360, 88));
        window.setResizable(false);

        NewGoalController newGoalController = loader.getController();

        newGoalController.submitButton.setOnAction(GoalSetNameHandler(window, newGoalController));

        newGoalController.nameField.setOnAction(GoalSetNameHandler(window, newGoalController));


        window.showAndWait();
        return name;
    }

    private EventHandler<ActionEvent> GoalSetNameHandler(Stage window, NewGoalController newGoalController) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                name = newGoalController.nameField.getText();
                window.close();
            }
        };
    }
}

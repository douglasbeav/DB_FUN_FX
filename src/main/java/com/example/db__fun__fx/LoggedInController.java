package com.example.db__fun__fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Label wlcmLbl;
    @FXML
    private Label favTeachLbl;
    @FXML
    private Button logoutBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Utils.changeScene(event, "main.fxml", "Log in", null, null);
            }
        });
    }

    public void setUserInformation(String username, String favTeacher) {
        wlcmLbl.setText("Welcome " + username + "!");
        favTeachLbl.setText("Your favorite teacher is " + favTeacher + "!");
    }
}

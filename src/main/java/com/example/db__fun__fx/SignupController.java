package com.example.db__fun__fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private Button signupBtn;
    @FXML
    private Button loginBtn;
    @FXML
    private TextField usrTxtFld;
    @FXML
    private PasswordField pswrdFld;
    @FXML
    private RadioButton douglasRB;
    @FXML
    private RadioButton andersRB;
    @FXML
    private RadioButton saifRB;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        douglasRB.setToggleGroup(toggleGroup);
        andersRB.setToggleGroup(toggleGroup);
        saifRB.setToggleGroup(toggleGroup);

        douglasRB.setSelected(true);

        signupBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if (!usrTxtFld.getText().trim().isEmpty() && !pswrdFld.getText().trim().isEmpty()) {
                    Utils.signUpUser(event, usrTxtFld.getText(), pswrdFld.getText(), toggleName);
                } else {
                    System.out.println("Please fill in all info");
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in all info");
                    alert.show();
                }
            }
        });
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Utils.changeScene(event, "main.fxml", "Log in!", null, null);
            }
        });
    }
}

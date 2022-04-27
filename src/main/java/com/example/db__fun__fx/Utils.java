package com.example.db__fun__fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class Utils {

    private static PreparedStatement pstmt = null;
    private static PreparedStatement psInsert = null;
    private static PreparedStatement psCheckUserExists = null;
    private static ResultSet rs = null;
    private static Connection connect = null;
    private static String url = System.getenv("url"); //"jdbc:mysql://localhost:3306/company";
    private static String usr = System.getenv("usr"); //"root";
    private static String pass = System.getenv("pass"); //"12345678";


    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username, String favTeacher) {
        Parent root = null;

        if (username != null && favTeacher != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Utils.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(username, favTeacher);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(Utils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

//    Method to sign up a new user
    public static void signUpUser(ActionEvent event, String usrName, String password, String favTeacher) {
        try {

            connection();
            psCheckUserExists = connect.prepareStatement("select * from users where usrName = ?");
            psCheckUserExists.setString(1, usrName);
            rs = psCheckUserExists.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR, "User already exists");
                alert.show();
            } else {
                psInsert = connect.prepareStatement("insert into users(usrName, password, favTeacher) values (?, ?, ?)");
                psInsert.setString(1, usrName);
                psInsert.setString(2, password);
                psInsert.setString(3, favTeacher);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Welcome!", usrName, favTeacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

//    Method to sign up a new user stored procedure
    public static void signUpUser_SP(ActionEvent event, String usrName, String password, String favTeacher) {
        try {

            connection();
            psCheckUserExists = connect.prepareStatement("select * from users where usrName = ?");
            psCheckUserExists.setString(1, usrName);
            rs = psCheckUserExists.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR, "User already exists");
                alert.show();
            } else {
                psInsert = connect.prepareCall("{CALL addUser(?, ?, ?)}");
                psInsert.setString(1, usrName);
                psInsert.setString(2, password);
                psInsert.setString(3, favTeacher);
                psInsert.executeUpdate();

                changeScene(event, "logged-in.fxml", "Welcome!", usrName, favTeacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public static void loginUser(ActionEvent event, String usrName, String password) {
        try {
            connection();
            pstmt = connect.prepareStatement("select password, favTeacher from users where usrName = ?");
            pstmt.setString(1, usrName);
            rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User not found");
                Alert alert = new Alert(Alert.AlertType.ERROR, "User not found");
                alert.show();
            } else {
                while (rs.next()) {
                    String retrievedPassword = rs.getString("password");
                    String retrievedFavTeacher = rs.getString("favTeacher");

                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "logged-in.fxml", "Welcome!", usrName, retrievedFavTeacher);
                    } else {
                        System.out.println("Passwords didn't match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private static void connection() {
        try {
            connect = DriverManager.getConnection(url, usr, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void closeConnection() {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (psCheckUserExists != null) {
            try {
                psCheckUserExists.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (psInsert != null) {
            try {
                psInsert.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connect != null) {
            try {
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

module com.example.db__fun__fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.db__fun__fx to javafx.fxml;
    exports com.example.db__fun__fx;
}
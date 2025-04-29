module com.example.automatingtextprocessing {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.automatingtextprocessing to javafx.fxml;
    exports com.example.automatingtextprocessing;
    exports com.example.automatingtextprocessing.controller;
    opens com.example.automatingtextprocessing.controller to javafx.fxml;
}
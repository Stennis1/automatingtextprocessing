package com.example.automatingtextprocessing.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private void handleLoadFile() {
        System.out.println("Load File Clicked!");
    }

    @FXML
    private void handleSaveFile() {
        System.out.println("Save File Clicked");
    }

    @FXML
    private void handleSearch() {
        System.out.println("Search Clicked!");
    }

    @FXML
    private void handleReplace() {
        System.out.println("Replace Clicked!");
    }
}
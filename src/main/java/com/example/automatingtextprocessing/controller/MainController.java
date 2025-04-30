package com.example.automatingtextprocessing.controller;

import com.example.automatingtextprocessing.model.FileUtils;
import com.example.automatingtextprocessing.model.RegexUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private TextField regexField;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField textField;

    @FXML
    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try{
                String content = FileUtils.readFile(file.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Error loading file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSaveFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                FileUtils.writeFile(file.getAbsolutePath(), textArea.getText());
                showAlert("File saved!");
            } catch (IOException e) {
                showAlert("Error saving file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSearch() {
        String text = textArea.getText();
        String regex = regexField.getText();

        List<String> results = RegexUtils.findMatches(text, regex);
        showAlert("Matches found: " + results.size() + "\n" + String.join("\n", results));
    }

    @FXML
    private void handleReplace() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Replace Text");
        dialog.setHeaderText("Text to Replace: ");
        dialog.setContentText("Replace with: ");
        dialog.showAndWait().ifPresent(replacement -> {
            String result = RegexUtils.replaceMatches(textArea.getText(), regexField.getText(), replacement);
            textArea.setText(result);
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
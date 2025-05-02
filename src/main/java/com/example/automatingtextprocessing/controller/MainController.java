package com.example.automatingtextprocessing.controller;

import com.example.automatingtextprocessing.model.DataEntry;
import com.example.automatingtextprocessing.model.FileUtils;
import com.example.automatingtextprocessing.model.RegexUtils;
import com.example.automatingtextprocessing.model.TextAnalysis;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MainController {

    @FXML
    private TextField regexField;

    @FXML
    private TextArea textArea;

    @FXML
    private Label wordCountLabel;

    private final DataManager dataManager = new DataManager();

    private void logError(String message, Exception e) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt", true))) {
            writer.write(LocalDateTime.now() + " - " + message + " - " + e.getMessage());
            writer.newLine();
        } catch (IOException ioException) {
            System.err.println("Failed to write to log file: " + ioException.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String prompt(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Input Required");
        dialog.setHeaderText(null);
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        return result.filter(s -> !s.trim().isEmpty()).orElse(null);
    }

    @FXML
    private void handleAnalyzeText() {
        try {
            String text = textArea.getText();
            Map<String, Long> freqMap = TextAnalysis.wordFrequency(text);
            List<Map.Entry<String, Long>> topWords = TextAnalysis.topNWords(freqMap, 3);

            long sentences = TextAnalysis.sentenceCount(text);
            long count = TextAnalysis.wordCount(text);

            StringBuilder result = new StringBuilder("Text Summary:\n");
            result.append("Total sentences: ").append(sentences).append("\n");
            result.append("Total words: ").append(count).append("\n\n");
            result.append("Top 3 Frequent Words:\n");
            topWords.forEach(entry -> result.append(entry.getKey())
                    .append(": ").append(entry.getValue()).append("\n"));

            showAlert(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logError("Failed to analyze text", e);
            showAlert("Error analyzing text. Please try again.");
        }
    }

    @FXML
    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                textArea.setText(content);
            } catch (IOException e) {
                logError("Failed to load file", e);
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
                showAlert("File saved successfully!");
            } catch (IOException e) {
                logError("Failed to save file", e);
                showAlert("Error saving file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleSearch() {
        String text = textArea.getText();
        String regex = regexField.getText();

        List<String> results = RegexUtils.findMatches(text, regex);
        if (results.isEmpty()) {
            showAlert("No match found.");
            return;
        }
        showAlert("Matches found: " + results.size() + "\n\n" + String.join("\n", results));

        String firstMatch = results.get(0);
        int index = text.indexOf(firstMatch);
        if (index >= 0) {
            textArea.selectRange(index, index + firstMatch.length());
        }
    }


    @FXML
    private void handleReplace() {
        String regex = regexField.getText();
        if (regex == null || regex.isEmpty()) {
            showAlert("Please enter a regex pattern first.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Replace Text");
        dialog.setHeaderText("Enter replacement text:");
        dialog.setContentText("Replace with:");

        dialog.showAndWait().ifPresent(replacement -> {
            String result = RegexUtils.replaceMatches(textArea.getText(), regex, replacement);
            textArea.setText(result);
        });
    }

    @FXML
    private void handleAddEntry() {
        String key = prompt("Enter key:");
        String value = prompt("Enter value:");

        if (key != null && value != null) {
            boolean added = dataManager.addEntry(new DataEntry(key, value));
            showAlert(added ? "Entry added." : "Duplicate entry. Not added.");
        }
    }

    @FXML
    private void handleRemoveEntry() {
        String keyToRemove = prompt("Enter key to remove:");
        if (keyToRemove != null) {
            boolean removed = dataManager.removeEntry(keyToRemove);
            showAlert(removed ? "Entry removed" : "Key not found!");
        }
    }

    @FXML
    private void handleShowEntries() {
        StringBuilder builder = new StringBuilder("Data Entries:\n");
        dataManager.getAllEntries().forEach(e -> builder.append(e).append("\n"));
        showAlert(builder.toString());
    }

    @FXML
    private void handleClearEntries() {
        dataManager.clearAllEntries();
        showAlert("All entries cleared.");
    }
}

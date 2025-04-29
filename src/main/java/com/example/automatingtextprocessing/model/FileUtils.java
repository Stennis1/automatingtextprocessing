package com.example.automatingtextprocessing.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static String readFile(String Path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(Path)));
    }

    public static void writeFile(String Path, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Path))) {
            writer.write(content);
        }
    }
}

package com.sirma.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {

    private final File file;

    public InputFileReader(File file) {
        this.file = file;
    }

    public List<String> readFile() {
        List<String> content = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    content.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}

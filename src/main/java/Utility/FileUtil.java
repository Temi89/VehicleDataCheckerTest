package Utility;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.*;

public class FileUtil {

    // Reads all lines from the given input file and returns them as a list
    public static List<String> readLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    // Appends a line to the output file. Adds header if file is empty
    public static void appendLine(String filePath, String line) throws IOException {
        File outputFile = new File(filePath);

        // Check if the  file is empty and if so, add the header
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            if (outputFile.length() == 0) {
                writer.write("VARIANT_REG,MAKE,MODEL,YEAR");
                writer.newLine(); // Adds a new line after header
            }
            writer.write(line);
            writer.newLine(); // Adds a new line after each appended line
        }
    }

    public static boolean compareFiles(String filePath1, String filePath2) throws IOException {
        List<String> file1Lines = FileUtil.readLines(filePath1);
        List<String> file2Lines = FileUtil.readLines(filePath2);

        // Parse each file into sets of records (e.g., vehicle details)
        Set<String> file1Data = parseFileData(file1Lines);
        Set<String> file2Data = parseFileData(file2Lines);

        // Compare the two sets (this ignores order)
        if (file1Data.equals(file2Data)) {
            return true;  // Files match (same data, regardless of order)
        }

        // If there is a mismatch, identify what's missing or different
        Set<String> missingInFile2 = new HashSet<>(file1Data);
        missingInFile2.removeAll(file2Data);  // Items in file1 but not in file2

        Set<String> missingInFile1 = new HashSet<>(file2Data);
        missingInFile1.removeAll(file1Data);  // Items in file2 but not in file1

        // Report differences
        if (!missingInFile2.isEmpty()) {
            System.out.println("Items missing in File 2:");
            missingInFile2.forEach(item -> System.out.println("File 1: " + item));
        }

        if (!missingInFile1.isEmpty()) {
            System.out.println("Items missing in File 1:");
            missingInFile1.forEach(item -> System.out.println("File 2: " + item));
        }

        return false;  // Return false if files don't match
    }

    // Helper method to parse file data into a set of records (vehicle details)
    private static Set<String> parseFileData(List<String> lines) {
        Set<String> data = new HashSet<>();
        for (String line : lines) {
            if (!line.startsWith("VARIANT_REG")) {  // Skip the header line
                data.add(line.trim());  // Add each line as a record to the set
            }
        }
        return data;
    }
}
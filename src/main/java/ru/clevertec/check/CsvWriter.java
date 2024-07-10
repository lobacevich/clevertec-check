package main.java.ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {

    private static final CsvWriter INSTANCE = new CsvWriter();

    private CsvWriter() {
    }

    public static CsvWriter getInstance() {
        return INSTANCE;
    }

    public void writeDataToCsv(String data, String filePath) throws BadRequestException {
        createDirectoryIfNotExists(filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("CsvWriter: Error write data");
            throw new BadRequestException();
        }
    }

    public List<String> loadListDataFromCsv(String filePath) throws BadRequestException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> data = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    data.add(line);
                }
            }
            return data;
        } catch (IOException e) {
            System.out.println("CsvWriter: Error load data");
            throw new BadRequestException();
        }
    }

    private void createDirectoryIfNotExists(String relativeFilePath) throws BadRequestException {
        Path filePath = FileSystems.getDefault().getPath(relativeFilePath);
        Path directoryPath = filePath.getParent();
        if (directoryPath != null && Files.notExists(directoryPath)) {
            try {
                Files.createDirectories(directoryPath);
                System.out.println("Directory " + directoryPath + "is created");
            } catch (IOException e) {
                throw new BadRequestException();
            }
        }
    }
}

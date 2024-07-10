package ru.clevertec.check.csv;

import ru.clevertec.check.exception.BadRequestException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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

package main.java.ru.clevertec.check;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWriter {

    private static final CsvWriter INSTANCE = new CsvWriter();

    private CsvWriter() {
    }

    public static CsvWriter getInstance() {
        return INSTANCE;
    }

    public void writeDataToCsv(String data, String filePath) throws InternalServerErrorException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.println("CsvWriter: Error load data");
            throw new InternalServerErrorException();
        }
    }

    public List<String> loadListDataFromCsv(String filePath) throws InternalServerErrorException {
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
            throw new InternalServerErrorException();
        }
    }
}

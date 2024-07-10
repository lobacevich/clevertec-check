package main.java.ru.clevertec.check;

public interface ApplicationService {

    void loadData() throws BadRequestException;

    void parseArgs(String[] args) throws BadRequestException;

    void generateCheck(StringBuilder builder) throws BadRequestException, NotEnoughMoneyException;

    void printCheck(String check);

    void setSaveToFile(String saveToFile);
}

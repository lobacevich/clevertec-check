package main.java.ru.clevertec.check;

public class BadRequestException extends Exception {

    @Override
    public String getMessage() {
        return "ERROR\nBAD REQUEST";
    }
}

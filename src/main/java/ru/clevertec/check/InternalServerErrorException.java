package main.java.ru.clevertec.check;

public class InternalServerErrorException extends Exception {

    @Override
    public String getMessage() {
        return "ERROR\nINTERNAL SERVER ERROR";
    }
}

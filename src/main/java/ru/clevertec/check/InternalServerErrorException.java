package main.java.ru.clevertec.check;

public class InternalServerErrorException extends RuntimeException {

    @Override
    public String getMessage() {
        return "ERROR\nINTERNAL SERVER ERROR";
    }
}

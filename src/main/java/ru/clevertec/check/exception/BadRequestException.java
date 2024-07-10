package ru.clevertec.check.exception;

public class BadRequestException extends Exception {

    @Override
    public String getMessage() {
        return "ERROR\nBAD REQUEST";
    }
}

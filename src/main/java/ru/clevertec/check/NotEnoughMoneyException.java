package main.java.ru.clevertec.check;

public class NotEnoughMoneyException extends Exception {

    @Override
    public String getMessage() {
        return "NOT ENOUGH MONEY";
    }
}

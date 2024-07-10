package ru.clevertec.check.service;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.NotEnoughMoneyException;

public interface ApplicationService {

    void parseArgs(String[] args) throws BadRequestException;

    void generateCheck(StringBuilder builder) throws BadRequestException, NotEnoughMoneyException;

    void printCheck(String check);

    void setSaveToFile(String[] args) throws BadRequestException;

    void createConnection();
}

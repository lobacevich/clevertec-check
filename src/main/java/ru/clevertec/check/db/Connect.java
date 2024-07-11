package ru.clevertec.check.db;

import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static final Connection CONNECTION;

    private Connect() {
    }

    static {
        try {
            CONNECTION = DriverManager.getConnection(System.getProperty("datasource.url"),
                    System.getProperty("datasource.username"),
                    System.getProperty("datasource.password"));
        } catch (
                SQLException e) {
            throw new InternalServerErrorException("Connection failed");
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}

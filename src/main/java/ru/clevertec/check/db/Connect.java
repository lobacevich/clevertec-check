package ru.clevertec.check.db;

import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static Connection CONNECTION;

    private Connect() {
    }

    public static void createConnection(String url, String username, String password) {
        try {
            CONNECTION = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new InternalServerErrorException("Connection failed");
        }
    }

    public static Connection getConnection() {
        return CONNECTION;
    }
}

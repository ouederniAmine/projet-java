package com.example.server.data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    private static final String JDBC_URL = "jdbc:mysql://avnadmin:AVNS_7QKL-FmJwUuc616QS8J@mysql-1dfd5f7d-java-project-fsb.a.aivencloud.com:20868/defaultdb?ssl-mode=REQUIRED";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = "AVNS_7QKL-FmJwUuc616QS8J";

    private static Connection connection;

    private DatabaseConnectionManager() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        }
        return connection;
    }
}

package com.xecore.superclans.database;


import com.xecore.superclans.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String JDBC_URL = Config.getProperty("database.url");
    private static final String USER = Config.getProperty("database.username");
    private static final String PASSWORD = Config.getProperty("database.password");

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}

package com.xecore.superclans.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public static void createTables() {
        String sql = """
            CREATE TABLE IF NOT EXISTS CLANS (
                ID INT PRIMARY KEY AUTO_INCREMENT,
                CLAN_NAME VARCHAR(255) NOT NULL,
                OWNER_UUID VARCHAR(36) NOT NULL
            );
        """;

        String sql1 = """
            CREATE TABLE IF NOT EXISTS MEMBERS (
                MEMBER_UUID VARCHAR(36) PRIMARY KEY,
                MEMBER_NAME VARCHAR(255) NOT NULL,
                CLAN_ID INT,
                FOREIGN KEY (CLAN_ID) REFERENCES CLANS(ID) ON DELETE SET NULL
            );
        """;


        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
            statement.execute(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

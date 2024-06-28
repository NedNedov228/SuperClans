package com.xecore.superclans.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUpdate {

    public static void deleteMemberFromClan(String memberUUID,int clanID) {
        //using prepared statement set clanId to null for the member
        String sql = "UPDATE MEMBERS SET CLAN_ID = NULL WHERE MEMBER_UUID = ? AND clan_id = ?";

        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, memberUUID);
            preparedStatement.setInt(2, clanID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete clan
    public static void deleteClan(int clanID) {
        String sql = "DELETE FROM CLANS WHERE ID = ?";
        try (Connection connection = DBConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clanID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

package com.xecore.superclans.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBQuery {
    public static List<String> getClans() {
        String sql = "SELECT CLAN_NAME FROM CLANS";
        List<String> clans = new ArrayList<>();
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                clans.add(resultSet.getString("CLAN_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clans;
    }

    // Find All Members of a Clan
    public static List<String> findMemberNames(int clanId) {
        String sql = "SELECT MEMBER_NAME FROM MEMBERS WHERE CLAN_ID = " + clanId;
        List<String> members = new ArrayList<>();
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                members.add(resultSet.getString("MEMBER_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public static String findClanNameById(int id) {
        String sql = "SELECT CLAN_NAME FROM CLANS WHERE ID = " + id;
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("CLAN_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findClanByName(String name) {
        String sql = "SELECT CLAN_NAME FROM CLANS WHERE CLAN_NAME = '" + name + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("CLAN_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int findClanIdByName(String name) {
        String sql = "SELECT ID FROM CLANS WHERE CLAN_NAME = '" + name + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Find Clan ID by Member UUID
    public static int findClanIdByMemberUUID(String memberUUID) {
        String sql = "SELECT CLAN_ID FROM MEMBERS WHERE MEMBER_UUID = '" + memberUUID + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                if(resultSet.getInt("CLAN_ID") != 0)
                    return resultSet.getInt("CLAN_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    // Find Clan ID by Member Name
public static int findClanIdByMemberName(String memberName) {
        String sql = "SELECT CLAN_ID FROM MEMBERS WHERE MEMBER_NAME = '" + memberName + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                if (resultSet.getInt("CLAN_ID") != 0)
                    return resultSet.getInt("CLAN_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //findClanNameByMemberName use JOINS
    public static String findClanNameByMemberName(String memberName) {
        String sql = "SELECT CLAN_NAME FROM CLANS JOIN MEMBERS ON CLANS.ID = MEMBERS.CLAN_ID WHERE MEMBERS.MEMBER_NAME = '" + memberName + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("CLAN_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findMemberUUIDByName(String memberName) {
        String sql = "SELECT MEMBER_UUID FROM MEMBERS WHERE MEMBER_NAME = '" + memberName + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("MEMBER_UUID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find Clan Owner by Member UUID by refernece CLAN_ID=CLANS.ID
    public static String findClanOwnerByMemberUUID(String memberUUID) {
        String sql = "SELECT OWNER_UUID FROM CLANS WHERE ID = (SELECT CLAN_ID FROM MEMBERS WHERE MEMBER_UUID = '" + memberUUID + "')";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("OWNER_UUID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findClanOwnerByID(int clanID) {
        String sql = "SELECT OWNER_UUID FROM CLANS WHERE ID = " + clanID;
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("OWNER_UUID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String findMemberByUUID(String memberUUID) {
        String sql = "SELECT MEMBER_NAME FROM MEMBERS WHERE MEMBER_UUID = '" + memberUUID + "'";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString("MEMBER_NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find Clan by Member UUID
    public static String findClanNameByMemberUUID(String memberUUID) {
        String sql = "SELECT CLAN_NAME FROM CLANS WHERE ID = (SELECT CLAN_ID FROM MEMBERS WHERE MEMBER_UUID = '" + memberUUID + "')";
        try (Connection connection = DBConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            String res = null;
            if (resultSet.next()) {
                 res = resultSet.getString("CLAN_NAME");
            }

                if (res != null
                        && !res.isEmpty()
                        && !res.isBlank()
                        && !res.equals("null")) {
                    return res;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


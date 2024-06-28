package com.xecore.superclans.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBInsert {

    public static String insertClan( String name , String owner){
        if (DBQuery.findClanByName(name) == null)
        {
            String sql = "INSERT INTO clans(clan_name,OWNER_UUID) VALUES(?,?)";

            try {
                Connection conn = DBConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, owner);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return "Clan " + name + " has been created";
        }
        else return "Clan " + name + " already exists";
    }
    public static void insertMember( String memberUUID,String memberName){
        String sql = "INSERT INTO members(member_uuid ,member_name) VALUES(?,?)";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memberUUID);
            pstmt.setString(2, memberName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertClanMember( String memberUUID, int clanId){

        String sql = "UPDATE members SET clan_id = ? WHERE member_uuid = ?";
        try {
            Connection conn = DBConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, clanId);
            pstmt.setString(2, memberUUID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Drop Table clans and clan_members
    public static void dropTables(){
        String sql = "DROP TABLE IF EXISTS clans";
        String sql2 = "DROP TABLE IF EXISTS members";

        try{
            Connection conn = DBConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

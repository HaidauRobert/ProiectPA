package dao;

import database.Database;

import java.sql.*;

public class UserDAO {

    public boolean register(String name, String password) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id from users where name='" + name + "'")) {
            if (rs.next() == false) {
                try (PreparedStatement pstmt = con.prepareStatement("insert into users (name, password) values (?,?)")) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, password);
                    pstmt.executeUpdate();
                    con.commit();
                    return true;
                }
            } else
                return false;
        }
    }

    public int login(String name, String password) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id from users where name='" + name + "' and password='" + password + "'" )) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}

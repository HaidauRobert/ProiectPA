package dao;
import java.sql.*;
import database.Database;
public class UserDAO {
    public void create(String username, String password) throws SQLException {
        Connection con = Database.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("insert into users (username,password) values (?,?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            con.commit();
        }
    }
}

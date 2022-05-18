package dao;

import database.Database;
import models.Node;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NodeDAO {
    public ArrayList<Node> genereazaHarta() throws SQLException {
        ArrayList<Node> puncte= new ArrayList<>();
        Connection con = Database.getConnection();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name, x, y from nodes")) {
            rs.next();
            do {
                Node nod = new Node(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                puncte.add(nod);
            }
            while (rs.next());
        }
        return puncte;
    }
}

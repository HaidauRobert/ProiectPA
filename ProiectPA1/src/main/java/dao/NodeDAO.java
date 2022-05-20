package dao;

import database.Database;
import models.Node;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class NodeDAO {
    public ArrayList<Node> genereazaHarta(int harta) throws SQLException {
        ArrayList<Node> puncte = new ArrayList<>();
        Connection con = Database.getConnection();
        if (harta == 0) {
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select id, name, x, y from nodes")) {
                rs.next();
                do {
                    Node nod = new Node(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    puncte.add(nod);
                }
                while (rs.next());
            }
        } else if (harta == 1) {
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select id, name, x, y from nodes1")) {
                rs.next();
                do {
                    Node nod = new Node(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    puncte.add(nod);
                }
                while (rs.next());
            }
        } else if (harta == 2) {
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select id, name, x, y from nodes2")) {
                rs.next();
                do {
                    Node nod = new Node(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    puncte.add(nod);
                }
                while (rs.next());
            }
        }
        return puncte;
    }
}

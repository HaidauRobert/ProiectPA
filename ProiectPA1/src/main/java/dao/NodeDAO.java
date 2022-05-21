package dao;

import database.Database;
import models.Node;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NodeDAO {

    public boolean checkExistence(Node node) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement continentStmt = con.createStatement();
             ResultSet rs = continentStmt.executeQuery(
                "select count(id) from nodes where name ='" + node.getName() + "'"+ "and x='" + node.getX() + "'"
                                              + "and y='" + node.getY() + "'" + "and nr_map='" + node.getNrMap() + "'" )) {
            int countNodes = 0;

            while(rs.next()){
                countNodes = (rs.getInt(1));
            }
            if(countNodes == 0){
                con.close();
                 return false;
             }
            con.close();
             return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        con.close();
        return false;
    }
    public void createNode(Node newNode) throws SQLException {

        Connection con = Database.getConnection();
        if(!checkExistence(newNode)) {
            try (PreparedStatement nodeStmt = con.prepareStatement(
                    "insert into nodes (name, x, y, nr_map) values (?, ?, ?, ?)")) {
                nodeStmt.setString(1, newNode.getName());
                nodeStmt.setInt(2, newNode.getX());
                nodeStmt.setInt(3, newNode.getY());
                nodeStmt.setInt(4, newNode.getNrMap());
                nodeStmt.executeUpdate();
                con.commit();
                con.close();


            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Node findById(int id) throws SQLException {
        Connection con = Database.getConnection();
        try (Statement continentStmt = con.createStatement();
             ResultSet rs = continentStmt.executeQuery(
                     "select x, y from nodes where id='" + id + "'")) {
            return rs.next() ? (new Node(rs.getInt(1), rs.getInt(2))) : null;
        }
    }

    public List<Node> generateNodes(int nrMap) throws SQLException {

        List<Node> nodes = new ArrayList<>();
        Connection con = Database.getConnection();

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("select id, name, x, y from nodes where nr_map='" + nrMap + "'")) {
            while (rs.next()) {
                Node nod = new Node(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                nodes.add(nod);
            }

        }
        con.close();

        return nodes;
    }
}

package dao;

import database.Database;
import models.Node;
import models.Street;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StreetDAO {
    public ArrayList<Street> genereazaStrazi(int harta) throws SQLException {
        ArrayList<Street> strazi = new ArrayList<>();
        Connection con = Database.getConnection();
        if (harta==0) {
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select id, name, id_nod_1, id_nod_2 from streets")) {
                rs.next();
                do {
                    Street strada = new Street(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    strazi.add(strada);
                }
                while (rs.next());
            }
        }
        if (harta==1) {
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select id, name, id_nod_1, id_nod_2 from streets1")) {
                rs.next();
                do {
                    Street strada = new Street(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    strazi.add(strada);
                }
                while (rs.next());
            }
        }
        if (harta==2) {
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery("select id, name, id_nod_1, id_nod_2 from streets2")) {
                rs.next();
                do {
                    Street strada = new Street(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    strazi.add(strada);
                }
                while (rs.next());
            }
        }
        return strazi;
    }

}

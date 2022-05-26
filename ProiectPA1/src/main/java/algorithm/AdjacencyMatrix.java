package algorithm;

import dao.NodeDAO;
import dao.StreetDAO;
import models.Node;
import models.Street;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.sql.SQLException;
import java.util.List;

public class AdjacencyMatrix {
    //AdjacencyMatrix matrice = new AdjacencyMatrix();
    //        boolean[][] matrix = new boolean[25][25];
    //        matrix=matrice.matriceAdiacenta(1);
    Graph<Node, Street> graph = new SimpleGraph<>(Street.class);
    public void createGraph(int nrMap) throws SQLException {


        StreetDAO streetDAO = new StreetDAO();
        NodeDAO nodeDAO = new NodeDAO();
        List<Node> nodes = nodeDAO.generateNodes(nrMap);
        List<Street> streets = streetDAO.generateStreets(nrMap);

        for (Node node : nodes) {
            graph.addVertex(node);
        }

        for (Street street : streets) {
            graph.addEdge(nodeDAO.findById(street.getIdNodeStart()), nodeDAO.findById(street.getIdNodeEnd()), street);
        }


    }
    public boolean[][] matriceAdiacenta(int nrMap) throws SQLException
    {
        boolean[][] matrice = new boolean[50][50];
        createGraph(nrMap);
        StreetDAO streetDAO = new StreetDAO();
        NodeDAO nodeDAO = new NodeDAO();
        List<Node> nodes = nodeDAO.generateNodes(nrMap);
        List<Street> streets = streetDAO.generateStreets(nrMap);
        for (Street street : streets)
        {
            matrice[street.getIdNodeStart()][street.getIdNodeEnd()]=true;
            matrice[street.getIdNodeEnd()][street.getIdNodeStart()]=true;
        }
        return matrice;
    }
}

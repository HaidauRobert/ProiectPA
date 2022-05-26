package algorithm;

import dao.NodeDAO;
import models.Node;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class TestCycles {
    public List<List<Node>> obtineCicluri(int nrMap) throws SQLException {
        List<List<Node>> foundCycles = new ArrayList<>();
        NodeDAO nodeDAO = new NodeDAO();
        int[] nodes = new int[50];
        for (int i=1;i<50;i++)
            nodes[i]=i;
        AdjacencyMatrix matrice = new AdjacencyMatrix();
               boolean[][] adjMatrix;
               adjMatrix=matrice.matriceAdiacenta(nrMap);
        ElementaryCyclesSearch ecs = new ElementaryCyclesSearch(adjMatrix, nodes);
        List cycles = ecs.getElementaryCycles();
        for (int i = 0; i < cycles.size(); i++) {
            List cycle = (List) cycles.get(i);
            ArrayList<Node> nodeList = new ArrayList<>();
            for (int j = 0; j < cycle.size(); j++) {
                Integer node = (Integer) cycle.get(j);
                if (j < cycle.size() - 1) {
                    nodeList.add(nodeDAO.findById(node));
                } else {
                    nodeList.add(nodeDAO.findById(node));
                }
            }
            foundCycles.add(nodeList);
        }
       return foundCycles;
    }


}

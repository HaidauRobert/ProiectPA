package algorithm;

import dao.NodeDAO;
import dao.StreetDAO;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import models.Node;
import models.Street;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.sql.SQLException;
import java.util.*;

public class Route {
    Graph<Node, Street> graph = new SimpleGraph<>(Street.class);
    NodeDAO nodeDAO = new NodeDAO();
    Map<Integer, Integer> isVisited = new HashMap<>();
    Map<Street, Boolean> isEdgeVisited = new HashMap<>();
    List<Node> nodeList = new ArrayList<>();
    Stack<Integer> stack = new Stack<>();
    TestCycles testCicluri = new TestCycles();
    List<List<Node>> foundCycles1;
    List<List<Node>> foundCycles2;
    List<List<Node>> foundCycles3;
    int ok1 = 0, ok2 = 0, ok3 = 0;

    public Route() throws SQLException {
    }

    public void messageBox(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message Here...");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void createGraph(int nrMap) throws SQLException {


        StreetDAO streetDAO = new StreetDAO();

        List<Node> nodes = nodeDAO.generateNodes(nrMap);
        List<Street> streets = streetDAO.generateStreets(nrMap);

        for (Node node : nodes) {
            graph.addVertex(node);
        }

        for (Street street : streets) {
            graph.addEdge(nodeDAO.findById(street.getIdNodeStart()), nodeDAO.findById(street.getIdNodeEnd()), street);
        }

    }

    public List<Node> getCyclesFromNode(int idStartNode, int nrMap, int searchedLength, List<List<Node>> foundCycles) throws SQLException {
        createGraph(nrMap);
        for (Node node : graph.vertexSet()) {
            isVisited.put(node.getId(), 0);

        }

        for (Street street : graph.edgeSet()) {
            isEdgeVisited.put(street, false);
        }

        int nodeLengthList = 0;

        for (List<Node> list : foundCycles) {
            System.out.println(list);
            System.out.println(calculateLength(list));
        }

        for (List<Node> list : foundCycles) {
            nodeLengthList = calculateLength(list);
            if (list.contains(nodeDAO.findById(idStartNode))) {
                if (searchedLength >= nodeLengthList - 200 && searchedLength <= nodeLengthList + 200) {
                    {
                        messageBox("Lungimea rutei gasite este de: " + nodeLengthList);
                        return list;
                    }
                }
            }
        }

        return new ArrayList<>();


    }

    public int calculateLength(List<Node> nodeList) {

        int distance = 0;
        Node previousNode = nodeList.get(0);
        for (int index = 1; index < nodeList.size(); index++) {
            distance += graph.getEdge(previousNode, nodeList.get(index)).getLength();
            previousNode = nodeList.get(index);
        }
        distance += graph.getEdge(previousNode, nodeList.get(0)).getLength();
        return distance;
    }


    /* void dfs(int src, int parent, Stack<Integer> stack, Map<Integer, Integer> isVisited) throws SQLException {
        isVisited.put(src, 1);
        stack.push(src);
        Node foundNode = nodeDAO.findById(src);
        for (Node node : graph.vertexSet()){
            if (graph.containsEdge(node, foundNode)){
                if (isVisited.get(node.getId()) == 0) {
                    dfs(node.getId(), src, stack, isVisited);
                }
                else  if (isVisited.get(node.getId()) == 1){
                    if(node.getId() != parent){
                        ArrayList<Node> nodeList = new ArrayList<>();
                        nodeList.add(node);
                        int pos = stack.size()-1;
                        while(pos >= 0 && stack.get(pos) != node.getId()) {
                            nodeList.add(nodeDAO.findById(stack.get(pos)));
                            pos--;
                        }

                        foundCycles.add(nodeList);
                    }
                }
            }

        }
        isVisited.put(src,2);
        stack.pop();
    } */


    /*
    public void dfs(int idNode, int idNodeStart, Map<Integer, Boolean> isVisited, Map<Street, Boolean> isEdgeVisited, List<Node> nodeList, int searchedLength) throws SQLException {
        if (isVisited.get(idNode)) {
            if (idNode == idNodeStart) {
                if (nodeList.size() >= 3) {

                    foundCycleList = new ArrayList<>();
                    int nodeLengthList = calculateLength(nodeList);
                    if (searchedLength >= nodeLengthList - 10 && searchedLength <= nodeLengthList + 10) {

                        foundCycleList.addAll(nodeList);
                    }
                }
            }
        } else {

            isVisited.put(idNode, true);
            Node foundNode = nodeDAO.findById(idNode);
            nodeList.add(foundNode);

            for (Node node : graph.vertexSet())
                if (graph.containsEdge(node, foundNode) && !isEdgeVisited.get(graph.getEdge(node, foundNode))) {
                    isEdgeVisited.put(graph.getEdge(node, foundNode), true);
                    dfs(node.getId(), idNodeStart, isVisited, isEdgeVisited, nodeList, searchedLength);
                }

            isVisited.put(idNode, false);
            for (Street street : graph.edgeSet()) {
                isEdgeVisited.put(street, false);
            }
            isEdgeVisited.put(graph.getEdge(foundNode, nodeDAO.findById(idNodeStart)), true);
            nodeList.remove(nodeDAO.findById(idNode));
        }

    }
*/
}



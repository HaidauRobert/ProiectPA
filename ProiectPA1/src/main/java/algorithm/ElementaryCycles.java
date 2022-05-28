package algorithm;

import dao.NodeDAO;
import models.Node;
import java.sql.SQLException;
import java.util.*;

public class ElementaryCycles {

    private List<List<Node>> cycles = new ArrayList<>();
    private Map<Node, List<Node>> adjList= new HashMap<>();
    private List<Node> graphNodes = new ArrayList<>();
    private NodeDAO nodeDao = new NodeDAO();
    private Map<Node, Boolean> blocked = new HashMap<>();
    private Map<Node, List<Node>> nodeBList = new HashMap<>();
    private List<Integer> stack = new ArrayList<>();

    public ElementaryCycles(List<Node> graphNodes, int nrMap) throws SQLException {
        this.graphNodes = graphNodes;
        this.adjList = AdjacencyList.getAdjList(nrMap);
    }

    public List<List<Node>> getElementaryCycles() throws SQLException {
        this.cycles = new ArrayList<>();
        this.blocked = new HashMap<>(graphNodes.size());
        this.nodeBList = new HashMap<>(graphNodes.size());
        this.stack = new ArrayList<>();

        StrongConnectedComponents strongConnComp = new StrongConnectedComponents(this.adjList);
        int startNode = graphNodes.get(0).getId();

        while (true) {
            SCCResult sccResult = strongConnComp.getAdjacencyList(startNode);

            if (sccResult != null && sccResult.getAdjList() != null) {
                Map<Node, List<Node>> scc = sccResult.getAdjList();
                startNode = sccResult.getLowestNodeId();
                for(Map.Entry<Node, List<Node>> pair : scc.entrySet()){
                    if(!pair.getValue().isEmpty() && pair.getValue().size() > 0){
                        blocked.put(pair.getKey(), false);
                        nodeBList.put(pair.getKey(), new ArrayList<>());
                    }
                }

                this.findCycles(startNode, startNode, scc);
                startNode++;
            } else {

                break;
            }
        }

        return this.cycles;
    }

    private boolean findCycles(int currentNode, int startNode, Map<Node, List<Node>> adjList) throws SQLException {
        boolean flag = false;
        this.stack.add(currentNode);
        blocked.put(nodeDao.findById(currentNode), true);

        Node nodeRoot = nodeDao.findById(currentNode);
        for(Node node : adjList.get(nodeRoot)){
            int idNode = node.getId();
            if(idNode == startNode){
                List<Node> cycle = new ArrayList<>();
                for (int j = 0; j < this.stack.size(); j++) {
                    int index = this.stack.get(j);
                    cycle.add(nodeDao.findById(index));
                }
                cycles.add(cycle);
                flag = true;

            }else if(!blocked.get(nodeDao.findById(idNode))){
                if(findCycles(idNode, startNode, adjList)){
                    flag = true;
                }
            }
        }

        if (flag) {
            unblock(currentNode);
        } else {
            for(Node node : adjList.get(nodeDao.findById(currentNode))){

                if(!nodeBList.get(node).contains(currentNode)){
                   nodeBList.get(node).add(nodeDao.findById(currentNode));
                }
            }
        }

        this.stack.remove((Integer) currentNode);
        return flag;
    }

    private void unblock(int node) throws SQLException {

        Node foundNode = nodeDao.findById(node);
        blocked.put(foundNode, false);

        List<Node> nodesB = nodeBList.get(foundNode);

        while (nodesB.size() > 0) {
            Node nodeB = nodesB.get(0);
            nodesB.remove(0);
            if(blocked.get(nodeB))
                this.unblock(nodeB.getId());
            }
        }
    }



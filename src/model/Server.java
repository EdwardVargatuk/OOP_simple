package model;

import java.util.*;

/**
 * 21.07.2019 10:25
 *
 * @author Edward
 */
public class Server {

    private static final int MAX_NUMBER_OF_NODES = 15;

    private int number;
    private List<Node> nodes;

    /**
     * create and fill list of nodes
     *
     * @param number of Server
     */
    Server(int number) {
        this.number = number;
        fillListOfNodes();
    }

    /**
     * generate list of Nodes of random size from 1 to MAX_NUMBER_OF_NODES
     */
    private void fillListOfNodes() {
        nodes = new LinkedList<>();
        Random random = new Random();
        int numOfNodes = random.nextInt(Server.MAX_NUMBER_OF_NODES) + 1;
        for (int i = 0; i < numOfNodes; i++) {
            Node node = new Node(i + 1, false);
            nodes.add(node);
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Server)) return false;
        Server server = (Server) o;
        return getNumber() == server.getNumber() &&
                getNodes().equals(server.getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getNodes());
    }

    @Override
    public String toString() {
        return "\n" + "Server{" +
                "number=" + number +
                ", nodes=" + nodes +
                '}';
    }
}

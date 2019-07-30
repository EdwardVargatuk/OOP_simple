package model;

import java.util.*;

/**
 * 21.07.2019 10:25
 *
 * @author Edward
 */
public class Server implements FallibleWithInners {

    private static final int MAX_NUMBER_OF_NODES = 15;

    private final int number;
    private List<Node> nodes;
    private boolean failed;

    private void setFailed() {
        this.failed = true;
    }

    @Override
    public int getNumber() {
        return number;
    }

    List<Node> getNodes() {
        return nodes;
    }
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
        nodes = new ArrayList<>();
        Random random = new Random();
        int numOfNodes = random.nextInt(Server.MAX_NUMBER_OF_NODES - 1) + 1;
        for (int i = 0; i < numOfNodes; i++) {
            Node node = new Node(i + 1);
            nodes.add(node);
        }
    }

     @Override
    public String toString() {
        return "\n" + "Server{" +
                "number=" + number +
                ", nodes=" + nodes +
                '}';
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int number) {
        return getNodes().get(number);
    }

    @Override
    public int getSize() {
        return getNodes().size();
    }

    /**
     * fail node and all that goes after
     *
     * @param randomNode in nodes
     */
    void failNode(Node randomNode) {
        setFailed();
        for (int j = getNodes().indexOf(randomNode); j < this.getSize(); j++) {
            getNodes().get(j).setFailed();
        }
    }

    /**
     * fail all nodes in the server
     */
    void failAllNodes() {
        setFailed();
        getNodes().forEach(Node::setFailed);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Server)) return false;
        Server server = (Server) o;
        return getNumber() == server.getNumber() &&
                isFailed() == server.isFailed() &&
                Objects.equals(getNodes(), server.getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getNodes(), isFailed());
    }
}

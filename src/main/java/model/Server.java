package model;

import exeptions.TransactionFailedException;
import utils.MyOptional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 21.07.2019 10:25
 *
 * @author Edward
 */
public class Server implements FallibleWithInners {

    private static final int MAX_NUMBER_OF_NODES = 15;

    private final int number;
    private List<MyOptional<Node>> nodes;

    private void setTransactionPassed() {
        this.transactionPassed = true;
    }

    private boolean transactionPassed;


    @Override
    public int getNumber() {
        return number;
    }

    @Override
    public void doTransaction() throws TransactionFailedException {
        getNodes().forEach(node -> {
            try {
                if (node.isPresent()) {
                    node.get().doTransaction();
                }
            } catch (TransactionFailedException e) {
                throw new TransactionFailedException("Server № " + getNumber() + " not passed transaction", e);
            }
        });
        setTransactionPassed();
    }

    private List<MyOptional<Node>> getNodes() {
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
     * generate optional list of Nodes of random size from 1 to MAX_NUMBER_OF_NODES
     */
    private void fillListOfNodes() {
        nodes = new ArrayList<>();
        Random random = new Random();
        int numOfNodes = random.nextInt(Server.MAX_NUMBER_OF_NODES - 1) + 1;
        for (int i = 0; i < numOfNodes; i++) {
            Node node = new Node(i + 1);
            MyOptional<Node> optionalNode = new MyOptional<>(node);
            nodes.add(optionalNode);
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
    public boolean isTransactionPassed() {
        return transactionPassed;
    }

    @Override
    public MyOptional<?> getInnerFallible(int number) {
        return  getNodes().get(number).isPresent() ? getNodes().get(number) : MyOptional.empty();
    }

    @Override
    public List<MyOptional<? extends FallibleWithInners>> getAllPresentInnerFallible() {
        return  getNodes().stream().filter(MyOptional::isPresent).collect(Collectors.toList());
    }

    @Override
    public int getSize() {
        return getNodes().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Server)) return false;
        Server server = (Server) o;
        return getNumber() == server.getNumber() &&
                isTransactionPassed() == server.isTransactionPassed() &&
                Objects.equals(getNodes(), server.getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getNodes(), isTransactionPassed());
    }
}

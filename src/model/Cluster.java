package model;

import java.util.*;

/**
 * Cluster have constructor which autofill list of servers
 * Sends message and set to fail random node and all subsequent nodes
 * <p>
 * 21.07.2019 10:37
 *
 * @author Edward
 */
public class Cluster implements Failable {

    private static final int MAX_NUMBER_OF_SERVERS = 15;

    private List<Server> servers;

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public Cluster() {
        fillListOfServers();
    }

    /**
     * generate list of Servers of random size from 1 to MAX_NUMBER_OF_SERVERS
     */
    private void fillListOfServers() {
        servers = new LinkedList<>();
        Random random = new Random();
        int numOfNodes = random.nextInt(Cluster.MAX_NUMBER_OF_SERVERS) + 1;
        for (int i = 0; i < numOfNodes; i++) {
            Server server = new Server(i + 1);
            servers.add(server);
        }
    }

    /**
     * set random node's isFail to true in random server and all subsequent nodes
     */
    public void sendMessage() {
        Random random = new Random();
        Server randomServer = getServers().get(random.nextInt(servers.size()));
        List<Node> randomServerNodes = randomServer.getNodes();
        Node randomNode = randomServerNodes.get(random.nextInt(randomServerNodes.size()));
        for (int j = randomServerNodes.indexOf(randomNode); j < randomServerNodes.size(); j++) {
            randomServerNodes.get(j).setFail(true);
        }
        for (int i = getServers().indexOf(randomServer) + 1; i < getServers().size(); i++) {
            getServers().get(i).getNodes().forEach(node -> node.setFail(true));
        }
    }

    /**
     * checks if current server have a fail node
     *
     * @param serverNumber in cluster
     * @param nodeNumber   in server
     * @return fail status of node
     */
    @Override
    public boolean isFailed(int serverNumber, int nodeNumber) {
        Server testServer = getServers().get(serverNumber - 1);
        if (testServer != null) {
            Node testNode = testServer.getNodes().get(nodeNumber - 1);
            if (testNode != null) {
                return testNode.isFail();
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cluster)) return false;
        Cluster cluster = (Cluster) o;
        return getServers().equals(cluster.getServers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServers());
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "servers=" + servers +
                '}';
    }
}

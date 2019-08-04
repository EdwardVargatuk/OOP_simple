package model;

import exeptions.NoNodesFoundException;

import java.util.*;

/**
 * Cluster have constructor which autofill list of servers
 * Sends message and set to fail random node and all subsequent nodes
 * <p>
 * 21.07.2019 10:37
 *
 * @author Edward
 */
public class Cluster implements FallibleWithInners {

    private static final int MAX_NUMBER_OF_SERVERS = 15;
    private boolean failed;
    private List<Server> servers;

    private List<Server> getServers() {
        return servers;
    }

    private void setFailed() {
        this.failed = true;
    }

    public Cluster() {
        fillListOfServers();
    }

    /**
     * generate list of Servers of random size from 1 to MAX_NUMBER_OF_SERVERS
     */
    private void fillListOfServers() {
        servers = new ArrayList<>();
        Random random = new Random();
        int numOfNodes = random.nextInt(Cluster.MAX_NUMBER_OF_SERVERS - 1) + 1;
        for (int i = 0; i < numOfNodes; i++) {
            Server server = new Server(i + 1);
            servers.add(server);
        }
    }

    /**
     * set random node's failed in random server and all subsequent nodes
     */
    public void sendMessage() throws NoNodesFoundException {
        setFailed();
        Random random = new Random();
        Server randomServer = getServers().get(random.nextInt(this.getSize()));
        List<Node> randomServerNodes = randomServer.getNodes();
        if (randomServerNodes.size()>0) {
            Node randomNode = randomServerNodes.get(random.nextInt(randomServerNodes.size()));
            randomServer.failNode(randomNode);
            //all next servers must be failed
            for (int i = getServers().indexOf(randomServer) + 1; i < this.getSize(); i++) {
                getServers().get(i).failAllNodes();
            }
        } else throw new NoNodesFoundException("random server is empty");
    }


    @Override
    public String toString() {
        return "Cluster{" +
                "servers=" + servers +
                '}';
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public FallibleWithInners getInnerFallible(int number) {
        return getServers().get(number);
    }

    @Override
    public int getSize() {
        return getServers().size();
    }

    @Override
    public int getNumber() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cluster)) return false;
        Cluster cluster = (Cluster) o;
        return isFailed() == cluster.isFailed() &&
                Objects.equals(getServers(), cluster.getServers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFailed(), getServers());
    }
}

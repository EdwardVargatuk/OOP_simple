package app;

import model.Cluster;
import model.Node;
import model.Server;

import java.util.List;

/**
 * I divided the search into the search for the first fail node and first server with this node
 * <p>
 * 21.07.2019 10:27
 *
 * @author Edward
 */
public class FailSearchEngine {

    private Cluster cluster = new Cluster();

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    /**
     *
     * @param cluster for it's method
     * @param server which contains fail node
     * @return number of fail node
     */
    private int findFailedNode(Cluster cluster, Server server) {
        List<Node> nodes = server.getNodes();
        if (nodes.size() == 1 || cluster.isFailed(server.getNumber(), nodes.get(0).getNumber())) {
            return 1;
        }
        int result = 0;
        int left = 0;
        int right = nodes.size();
        int position = (left + right) / 2;
        while (position != 0 && position < nodes.size()) {
            if (cluster.isFailed(server.getNumber(), nodes.get(position).getNumber())) {
                if (!cluster.isFailed(server.getNumber(), nodes.get(position - 1).getNumber())) {
                    return nodes.get(position).getNumber();
                } else {
                    right = position - 1;
                }
            } else {
                left = position + 1;
            }
            result = nodes.get(position).getNumber();
            position = (left + right) / 2;
        }
        return result;
    }

    /**
     * @param servers of cluster
     * @return fail server
     */
    private Server findServerWithFailedNode(List<Server> servers) {
        if (servers.size() == 1 || servers.get(0).getNodes().get(servers.get(0).getNodes().size() - 1).isFail()) {
            return servers.get(0);
        }
        Server server = null;
        int left = 0;
        int right = servers.size();
        int position = (left + right) / 2;
        while (position != 0 && position < servers.size()) {
            List<Node> currentServerNodes = servers.get(position).getNodes();
            List<Node> previousServerNodes = servers.get(position - 1).getNodes();
            if (currentServerNodes.get(currentServerNodes.size() - 1).isFail()) {
                if (!previousServerNodes.get(previousServerNodes.size() - 1).isFail()) {
                    return servers.get(position);
                } else {
                    right = position - 1;
                }
            } else {
                left = position + 1;
            }
            server = servers.get(position);
            position = (left + right) / 2;
        }
        return server;
    }


    /**
     * @param cluster on which to search
     * @return result string
     */
    private String search(Cluster cluster) {
        String result = "";
        List<Server> servers = cluster.getServers();
        Server serverWithFailedNode = findServerWithFailedNode(servers);
        if (serverWithFailedNode != null) {
            int numberOfFailedNode = findFailedNode(cluster, serverWithFailedNode);
            if (numberOfFailedNode != 0) {
                result = "Server with number " + serverWithFailedNode.getNumber() + " has fail node with number " + numberOfFailedNode;
            }
        } else {
            result = "There is no failed node in cluster";
        }
        return result;
    }

    public static void main(String[] args) {
        FailSearchEngine failSearchEngine = new FailSearchEngine();
        Cluster cluster = failSearchEngine.getCluster();
        //send message
        cluster.sendMessage();
        System.out.println(cluster);
        //use search
        String result = failSearchEngine.search(cluster);
        System.out.println(result);

    }
}

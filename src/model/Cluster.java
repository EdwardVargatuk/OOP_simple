package model;

import exeptions.TransactionFailedException;
import utils.MyOptional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Cluster have constructor which autofill list of servers
 * Do Transaction and if it not passed successful set to fail all subsequent nodes
 * <p>
 * 21.07.2019 10:37
 *
 * @author Edward
 */
public class Cluster implements FallibleWithInners {

    private static final int MAX_NUMBER_OF_SERVERS = 15;
    private boolean transactionPassed;
    private List<MyOptional<Server>> servers;

    private List<MyOptional<Server>> getServers() {
        return servers;
    }

    private void setTransactionNotPassed() {
        this.transactionPassed = true;
    }

    public Cluster() {
        fillListOfServers();
    }

    /**
     * generate optional list of Servers of random size from 1 to MAX_NUMBER_OF_SERVERS
     */
    private void fillListOfServers() {
        servers = new ArrayList<>();
        Random random = new Random();
        int numOfNodes = random.nextInt(Cluster.MAX_NUMBER_OF_SERVERS - 1) + 1;
        for (int i = 0; i < numOfNodes; i++) {
            Server server = new Server(i + 1);
            MyOptional<Server> optionalServer = new MyOptional<>(server);
//            if (optionalServer.isPresent())
            servers.add(optionalServer);
        }
    }

    @Override
    public boolean isTransactionPassed() {
        return transactionPassed;
    }

    @Override
    public MyOptional<?> getInnerFallible(int number) {
        return getServers().get(number).isPresent() ? getServers().get(number) : MyOptional.empty();
    }

    @Override
    public List<MyOptional<? extends FallibleWithInners>> getAllPresentInnerFallible() {
        return  getServers().stream().filter(MyOptional::isPresent).collect(Collectors.toList());
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
    public void doTransaction() throws TransactionFailedException {
        getServers().forEach(server -> {
            try {
                if (server.isPresent()) {
                    server.get().doTransaction();
                }
            } catch (TransactionFailedException e) {
                throw new TransactionFailedException("Cluster has failed transaction", e);
            }
        });
        setTransactionNotPassed();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cluster)) return false;
        Cluster cluster = (Cluster) o;
        return isTransactionPassed() == cluster.isTransactionPassed() &&
                Objects.equals(getServers(), cluster.getServers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isTransactionPassed(), getServers());
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "servers=" + servers +
                '}';
    }

}

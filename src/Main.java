import search_engine.FailSearchEngine;
import exeptions.NotFoundFailedElements;
import exeptions.TransactionFailedException;
import model.Cluster;

/**
 * 29.07.2019 21:44
 *
 * @author Edward
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Cluster cluster = new Cluster();
        System.out.println(cluster);
        System.out.println("\n");

        //do transaction on cluster
        try {
            cluster.doTransaction();
            System.out.println("Cluster after transaction\n" + cluster);
            System.out.println("Transaction has passed successfully!");
        } catch (TransactionFailedException e) {
            transactionFailed(cluster, e);

        }


    }

    private static void transactionFailed(Cluster cluster, TransactionFailedException e) throws InterruptedException {
        System.out.println("Detected fail:");
        e.printStackTrace();
        System.out.println(cluster);

        Thread.sleep(10); // To guarantee stacktrace printing

        System.out.println("Searching transaction to confirm fail:");
        FailSearchEngine failSearchEngine = new FailSearchEngine();

        try {
            String result = failSearchEngine.findFailedElements(cluster);
            System.out.println(result);
        } catch (NotFoundFailedElements e1) {
            e.printStackTrace();
        }
    }

}

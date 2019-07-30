import app.FailSearchEngine;
import exeptions.NoNodesFoundException;
import exeptions.NotFoundFailedElements;
import model.Cluster;

/**
 * 29.07.2019 21:44
 *
 * @author Edward
 */
public class Main {

    public static void main(String[] args) {
        FailSearchEngine failSearchEngine = new FailSearchEngine();
        Cluster cluster = new Cluster();
        //send message
        try {
            cluster.sendMessage();
        } catch (NoNodesFoundException e) {
            e.printStackTrace();
        }
        System.out.println(cluster);
        System.out.println("\n");
        try {
            String result = failSearchEngine.findFailedElements(cluster);
            System.out.println(result);
        } catch (NotFoundFailedElements e) {
            e.printStackTrace();
        }
    }
}

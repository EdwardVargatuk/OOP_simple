package search_engine;

import com.google.gson.JsonObject;
import exeptions.NotFoundFailedElements;
import exeptions.TransactionFailedException;
import model.Cluster;
import model.Node;
import model.Server;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 19.08.2019 22:20
 *
 * @author Edward
 */
class FailSearchEngineTest {

    /**
     * if transaction is success and we try to find any failed elements, than must be thrown NotFoundFailedElements exception
     * if transaction is failed, we catch an exception that have info about failed elements and we can compare it with the result of findFailedElements()
     */
    @Test
    void findFailedElements() {
        FailSearchEngine failSearchEngine = new FailSearchEngine();
        Cluster cluster = new Cluster();
        try {
            cluster.doTransaction();
            assertThrows(NotFoundFailedElements.class, () -> failSearchEngine.findFailedElements(cluster));
        } catch (TransactionFailedException e) {
            try {
                String serverMessage = e.getCause().getMessage();
                String nodeMessage = e.getCause().getCause().getMessage();
                int serverNumber = Integer.parseInt((serverMessage.replaceAll("\\D+", "")));
                int nodeNumber = Integer.parseInt((nodeMessage.replaceAll("\\D+", "")));
                JsonObject failedElements = failSearchEngine.findFailedElements(cluster);
                int failedServerNumber = failedElements.get("Failed Server").getAsInt();
                int failedNodeNumber = failedElements.get("Failed Node").getAsInt();
                Server server = (Server) cluster.getInnerFallible(failedServerNumber).get();
                Node node = (Node) server.getInnerFallible(failedNodeNumber).get();
                assertFalse(server.isTransactionPassed());
                assertFalse(node.isTransactionPassed());
                assertEquals(serverNumber, failedServerNumber);
                assertEquals(nodeNumber, failedNodeNumber);
            } catch (NotFoundFailedElements notFoundFailedElements) {
                notFoundFailedElements.printStackTrace();
            }
        }

    }
}
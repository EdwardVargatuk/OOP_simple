import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import search_engine.FailSearchEngine;
import exeptions.NotFoundFailedElements;
import exeptions.TransactionFailedException;
import model.Cluster;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 29.07.2019 21:44
 *
 * @author Edward
 */
public class Main {

    private static final String PATH_OF_CLUSTER_BEFORE_TRANSACTION = "src/main/resources/beforeTransaction/cluster.json";
    private static final String PATH_OF_CLUSTER_AFTER_TRANSACTION = "src/main/resources/afterTransaction/cluster.json";
    private static final String PATH_OF_SEARCH_RESULT = "src/main/resources/afterTransaction/result.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws InterruptedException {
        Cluster cluster = new Cluster();

        Main.writeToJsonFile(cluster, PATH_OF_CLUSTER_BEFORE_TRANSACTION);
        System.out.println("Cluster before transaction:");
        System.out.println("--------------------------------------------------------------------------------------");
        Main.printToConsoleFromJsonFile(PATH_OF_CLUSTER_BEFORE_TRANSACTION);

        //do transaction on cluster
        try {
            cluster.doTransaction();
            Main.writeToJsonFile(cluster, PATH_OF_CLUSTER_AFTER_TRANSACTION);
            System.out.println("Cluster after transaction:");
            System.out.println("--------------------------------------------------------------------------------------");
            Main.printToConsoleFromJsonFile(PATH_OF_CLUSTER_AFTER_TRANSACTION);
            System.out.println("Transaction has passed successfully!");
        } catch (TransactionFailedException e) {
            transactionFailed(cluster, e);
        }
    }

    /**
     * @param filepath to read json file
     */
    private static void printToConsoleFromJsonFile(String filepath) {
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {

            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param object to write to json file
     * @param filepath target path
     */
    private static void writeToJsonFile(Object object, String filepath) {
        try (Writer writer = new FileWriter(filepath)) {

            gson.toJson(object, writer);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cluster to detect failed elements
     * @param e with cause of exception
     */
    private static void transactionFailed(Cluster cluster, TransactionFailedException e) throws InterruptedException {
        System.out.println("Detected fail:");
        e.printStackTrace();
        System.out.println("for better readability use toString");
        System.out.println(cluster);

        Thread.sleep(10); // To guarantee stacktrace printing

        System.out.println("Searching transaction to confirm fail:");
        FailSearchEngine failSearchEngine = new FailSearchEngine();

        try {
            JsonObject jsonObject = failSearchEngine.findFailedElements(cluster);
            Main.writeToJsonFile(jsonObject, PATH_OF_SEARCH_RESULT);
            Main.printToConsoleFromJsonFile(PATH_OF_SEARCH_RESULT);
        } catch (NotFoundFailedElements e1) {
            e.printStackTrace();
        }
    }

}

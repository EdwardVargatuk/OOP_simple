
import model.Cluster;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 29.07.2019 21:44
 *
 * @author Edward
 */
public class Main {

    private static int number = 0;

    public static synchronized void calculateNumber(){
        number +=3;
        System.out.println(number);
    }

    public static void main(String[] args) throws InterruptedException {
        Cluster cluster = new Cluster();
        ExecutorService service = Executors.newFixedThreadPool(10);

            IntStream.range(0, 10)
                    .forEach(count -> {

                        service.submit(cluster::doTransaction);
                        //for having pretty print
                        try {
                            service.awaitTermination(50, TimeUnit.MILLISECONDS);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(count + " Thread:\n" + cluster + "\n");
                    });

        service.awaitTermination(2000, TimeUnit.MILLISECONDS);

        //second example
        IntStream.range(0, 300)
                .forEach(number-> service.submit(Main::calculateNumber));

        service.awaitTermination(500, TimeUnit.MILLISECONDS);
        if (!service.isShutdown()) service.shutdown();
    }


}

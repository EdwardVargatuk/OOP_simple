package search;

public class Result {

    private int failedServer;
    private int failedNode;

    public Result(int failedNode) {
        this.failedNode = failedNode;
    }

    public void setFailedServer(int failedServer) {
        this.failedServer = failedServer;
    }

    @Override
    public String toString() {
        return "Result{" +
                "failedServer=" + failedServer +
                ", failedNode=" + failedNode +
                '}';
    }

}

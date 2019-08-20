package exeptions;

/**
 * 04.08.2019 16:49
 *
 * @author Edward
 */
public class TransactionFailedException  extends  RuntimeException {

    public TransactionFailedException(String message) {
        super(message);
    }

    public TransactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

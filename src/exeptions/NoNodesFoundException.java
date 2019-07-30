package exeptions;

/**
 * perhaps not a very appropriate exception, but for example
 * 30.07.2019 19:25
 *
 * @author Edward
 */
public class NoNodesFoundException extends Exception {

    public NoNodesFoundException(String message) {
        super(message);
    }

    public NoNodesFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

package exeptions;

/**
 * 29.07.2019 21:59
 *
 * @author Edward
 */
public class NotFoundFailedElements extends Exception {

    public NotFoundFailedElements(String message) {
        super(message);
    }

    public NotFoundFailedElements(String message, Throwable cause) {
        super(message, cause);
    }
}

package sigma.pinance.src.core.exceptions;

/**
 * AppExceptions should only occur when a Manager reaches an error state.
 */
public class AppException extends RuntimeException {
    public AppException(String message) {
        super(message);
    }
}

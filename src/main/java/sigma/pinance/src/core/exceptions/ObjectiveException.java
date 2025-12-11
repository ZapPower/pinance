package sigma.pinance.src.core.exceptions;

import lombok.Getter;

/**
 * ObjectiveExceptions should only occur within an Objective and when Objective operations fail.
 */
public class ObjectiveException extends RuntimeException {
    @Getter
    final Severity severity;
    public ObjectiveException(Severity severity, String message) {
        super(message);
        this.severity = severity;
    }
    public ObjectiveException(Severity severity, String message, Throwable cause) {
        super(message, cause);
        this.severity = severity;
    }
}

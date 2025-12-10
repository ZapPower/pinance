package sigma.pinance.src.core.exceptions;

import lombok.Getter;

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

package by.lashkevich.logic.entity;

public class JWDException extends RuntimeException {
    public JWDException() {
        super();
    }

    public JWDException(String message) {
        super(message);
    }

    public JWDException(String message, Throwable cause) {
        super(message, cause);
    }

    public JWDException(Throwable cause) {
        super(cause);
    }

    protected JWDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

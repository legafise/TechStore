package by.lashkevich.logic.dao.reader;

import by.lashkevich.logic.entity.JWDException;

public class PropertiesReaderException extends JWDException {
    public PropertiesReaderException() {
    }

    public PropertiesReaderException(String message) {
        super(message);
    }

    public PropertiesReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesReaderException(Throwable cause) {
        super(cause);
    }

    public PropertiesReaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package app.application.exception;

public class ACSException extends RuntimeException {

    public ACSException() {
    }

    public ACSException(String message) {
        super(message);
    }
}

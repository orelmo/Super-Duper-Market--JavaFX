package Exceptions;

public class IdAmbiguityException extends RuntimeException {
    public IdAmbiguityException() {

    }

    public IdAmbiguityException(String message) {
        super(message);
    }
}
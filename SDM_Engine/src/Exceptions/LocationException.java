package Exceptions;

public class LocationException extends RuntimeException {
    public LocationException() {

    }

    public LocationException(String message) {
        super(message);
    }
}
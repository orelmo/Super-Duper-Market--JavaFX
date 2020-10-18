package Exceptions;

public class NoSellerException extends RuntimeException {
    public NoSellerException() {

    }

    public NoSellerException(String message) {
        super(message);
    }
}
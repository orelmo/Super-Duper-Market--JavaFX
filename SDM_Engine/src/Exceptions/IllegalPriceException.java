package Exceptions;

public class IllegalPriceException extends RuntimeException {
    public IllegalPriceException(){
    }

    public IllegalPriceException(String message){
        super(message);
    }
}

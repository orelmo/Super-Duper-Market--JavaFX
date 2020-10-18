package Exceptions;

public class UnexistItemException extends RuntimeException {
    public UnexistItemException(){

    }

    public UnexistItemException(String message){
        super(message);
    }
}

package Exceptions;

public class UndefinedItemException extends RuntimeException {
    public UndefinedItemException(){

    }

    public UndefinedItemException(String message){
        super(message);
    }
}
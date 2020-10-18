package Exceptions;

public class CannotBeDeletedException extends RuntimeException {
    public CannotBeDeletedException(){
    }

    public CannotBeDeletedException(String message){
        super(message);
    }
}

package Exceptions;

public class FileTypeException extends RuntimeException {
    public FileTypeException(){

    }

    public FileTypeException(String message){
        super(message);
    }
}

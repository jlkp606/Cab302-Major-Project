package Exceptions;

public class IncorrectUserException extends Exception{
    public IncorrectUserException(String errorMessage) {
        super(errorMessage);
    }
}

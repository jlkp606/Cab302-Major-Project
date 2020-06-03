package Exceptions;

public class MissingPermissionException extends Exception{
    public MissingPermissionException(String errorMessage) {
        super(errorMessage);
    }
}

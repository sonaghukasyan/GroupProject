package im.aua.bankProject.exceptions;

public class InvalidPincodeException extends CardException{
    public InvalidPincodeException(){
        super("Pin code is wrong.");
    }
    public InvalidPincodeException(String message){
        super(message);
    }
}

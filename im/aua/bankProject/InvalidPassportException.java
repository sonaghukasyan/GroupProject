package im.aua.bankProject;

public class InvalidPassportException extends Exception{

    public InvalidPassportException(){
        super("Invalid passport");
    }

    public InvalidPassportException(String message){
        super(message);
    }
}

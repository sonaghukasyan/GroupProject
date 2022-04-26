package im.aua.bankProject;

public class CardException extends Exception{
    public CardException(){
        super("Illegal arrangement");
    }
    public CardException(String message){
        super(message);
    }
}


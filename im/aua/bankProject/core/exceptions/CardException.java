package im.aua.bankProject.core.exceptions;

public class CardException extends Exception{
    public CardException(){
        super("Card exception");
    }
    public CardException(String message){
        super(message);
    }
}


package im.aua.bankProject.exceptions;

public class CardException extends Exception{
    public CardException(){
        super("Card exception");
    }
    public CardException(String message){
        super(message);
    }
}


package im.aua.bankProject.core.exceptions;

public class CardIsBlockedException extends CardException{
    public CardIsBlockedException(){
        super("Card is blocked.");
    }
    public CardIsBlockedException(String message){
        super(message);
    }
}

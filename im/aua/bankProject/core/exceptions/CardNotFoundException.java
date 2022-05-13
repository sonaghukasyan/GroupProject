package im.aua.bankProject.core.exceptions;

public class CardNotFoundException extends CardException{
    public CardNotFoundException(){
        super("Card is not found.");
    }
    public CardNotFoundException(String message){
        super(message);
    }
}

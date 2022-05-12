package im.aua.bankProject.exceptions;

public class CardNotFoundException extends CardException{
    public CardNotFoundException(){
        super("Card is not found.");
    }
    public CardNotFoundException(String message){
        super(message);
    }
}

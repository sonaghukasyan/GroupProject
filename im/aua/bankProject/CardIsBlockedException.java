package im.aua.bankProject;

public class CardIsBlockedException extends CardException{
    public CardIsBlockedException(){
        super("Card is blocked.");
    }
    public CardIsBlockedException(String message){
        super(message);
    }
}

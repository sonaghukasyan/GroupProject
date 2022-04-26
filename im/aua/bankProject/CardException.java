package im.aua.bankProject;

public class CardException extends Exception{
    public CardException(){
        super("Card exception");
    }
    public CardException(String message){
        super(message);
    }
}


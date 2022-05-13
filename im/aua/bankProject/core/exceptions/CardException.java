package im.aua.bankProject.core.exceptions;

/**
 * The <code>CardException</code> represents the exceptions related to the cards.
 */
public class CardException extends Exception{
    /**
     * Constructs a <code>CardException</code> exception with default
     * message of type <code>String</code>.
     */
    public CardException(){
        super("Card exception");
    }

    /**
     * Constructs a <code>CardException</code> exception with the specified
     * message of type <code>String</code>.
     *
     * @param message      the <code>String</code> message
     */
    public CardException(String message){
        super(message);
    }
}


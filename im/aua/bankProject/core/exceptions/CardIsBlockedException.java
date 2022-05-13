package im.aua.bankProject.core.exceptions;

/**
 * The <code>CardIsBlockedException</code> class is a derived class of
 * <code>CardException</code> class and represents an exception which is thrown
 * when the card is blocked.
 */
public class CardIsBlockedException extends CardException{
    /**
     * Constructs a <code>CardIsBlockedException</code> exception with default
     * message of type <code>String</code>.
     */
    public CardIsBlockedException(){
        super("Card is blocked.");
    }

    /**
     * Constructs a <code>CardIsBlockedException</code> exception with the specified
     * message of type <code>String</code>.
     *
     * @param message      the <code>String</code> message
     */

    public CardIsBlockedException(String message){
        super(message);
    }
}

package im.aua.bankProject.core.exceptions;
/**
 * The <code>CardNotFoundException</code> class is a derived class of
 * <code>CardException</code> class and represents an exception which is thrown
 * when the card is not found.
 */
public class CardNotFoundException extends CardException{
    /**
     * Constructs a <code>CardNotFoundException</code> exception with default
     * message of type <code>String</code>.
     */

    public CardNotFoundException(){
        super("Card is not found.");
    }

    /**
     * Constructs a <code>CardNotFoundException</code> exception with the specified
     * message of type <code>String</code>.
     *
     * @param message      the <code>String</code> message
     */
    public CardNotFoundException(String message){
        super(message);
    }
}

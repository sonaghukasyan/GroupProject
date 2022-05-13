package im.aua.bankProject.core.exceptions;

/**
 * The <code>InvalidPincodeException</code> represents an exception which is thrown
 * when the pin code is not valid.
 */
public class InvalidPincodeException extends CardException{
    /**
     * Constructs a <code>InvalidPincodeException</code> exception with default
     * message of type <code>String</code>.
     */
    public InvalidPincodeException(){
        super("Pin code is wrong.");
    }

    /**
     * Constructs a <code>InvalidPincodeException</code> exception with the specified
     * message of type <code>String</code>.
     *
     * @param message      the <code>String</code> message
     */

    public InvalidPincodeException(String message){
        super(message);
    }
}

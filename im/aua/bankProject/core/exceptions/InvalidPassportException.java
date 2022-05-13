package im.aua.bankProject.core.exceptions;
/**
 * The <code>InvalidPassportException</code> represents an exception which is thrown
 * when the passport number is not valid.
 */

public class InvalidPassportException extends Exception{

    /**
     * Constructs a <code>InvalidPassportException</code> exception with default
     * message of type <code>String</code>.
     */
    public InvalidPassportException(){
        super("Invalid passport");
    }

    /**
     * Constructs a <code>InvalidPassportException</code> exception with the specified
     * message of type <code>String</code>.
     *
     * @param message      the <code>String</code> message
     */
    public InvalidPassportException(String message){
        super(message);
    }
}

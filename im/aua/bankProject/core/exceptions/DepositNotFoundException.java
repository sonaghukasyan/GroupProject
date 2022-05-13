package im.aua.bankProject.core.exceptions;

/**
 * The <code>DepositNotFoundException</code> class represents an exception which is thrown
 * when the deposit is not found.
 */

public class DepositNotFoundException extends Exception{

    /**
     * Constructs a <code>DepositNotFoundException</code> exception with default
     * message of type <code>String</code>.
     */

    public DepositNotFoundException(){
        super("Deposit is not found.");
    }

    /**
     * Constructs a <code>DepositNotFoundException</code> exception with the specified
     * message of type <code>String</code>.
     *
     * @param message      the <code>String</code> message
     */

    public DepositNotFoundException(String message){
        super(message);
    }
}

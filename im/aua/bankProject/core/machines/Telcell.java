package im.aua.bankProject.core.machines;

import im.aua.bankProject.core.exceptions.CardException;
import im.aua.bankProject.core.exceptions.CardNotFoundException;
import im.aua.bankProject.core.bankPrivate.Bank;
import im.aua.bankProject.core.bankPrivate.Card;

/**
 * The <code>Telcell</code> class represents the Telcell machine.
 */

public class Telcell {

    public static final double DEBIT_DEDUCTION = 300;
    public static final double CREDIT_DEDUCTION = 150;

    /**
     * This method adds the specified <code>double</code> money to the balance
     * of the card corresponding to the specified <code>long</code> card number.
     *
     * @param cardNumber     the <code>long</code> card number
     * @param money          the <code>double</code> money
     */
    public static double transferMoney(long cardNumber, double money) throws CardException {
        if(money <= CREDIT_DEDUCTION)
            throw new CardException("Not valid amount of money.");

        Card.CardType type = Bank.verifyCardAndGetType(cardNumber);
        if(type == null)
            throw new CardNotFoundException("Card not found");

        if(type == Card.CardType.CREDIT){
            return Bank.transferMoney(cardNumber,money - CREDIT_DEDUCTION);
        }
        else if(type == Card.CardType.DEBIT){
            if(money <= DEBIT_DEDUCTION)
                throw new CardException("Not enough funds.");
            return Bank.transferMoney(cardNumber,money - DEBIT_DEDUCTION);
        }
        return 0;
    }
}

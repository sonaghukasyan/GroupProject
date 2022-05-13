package im.aua.bankProject.core.machines;

import im.aua.bankProject.core.exceptions.CardException;
import im.aua.bankProject.core.exceptions.CardIsBlockedException;
import im.aua.bankProject.core.exceptions.CardNotFoundException;
import im.aua.bankProject.core.exceptions.InvalidPincodeException;
import im.aua.bankProject.core.bankPrivate.Bank;
import im.aua.bankProject.core.bankPrivate.Card;
/**
 * The <code>ATM</code> class represents the ATM.
 * An object of type <code>ATM</code> contains one instance variable of type
 * <code>int</code> representing the number of tries the person made to enter
 * the pin code, one instance variable of type <code> Card </code> representing
 * the card that have been inserted into the ATM, and one instance variable
 * of type <code>boolean</code> representing whether the pin code is checked
 * or not.
 */
public class ATM {
    private int tries;
    private Card card;
    public static final int maxPinCodeTries = 3;
    private boolean pinCodeChecked;

    /**
     * Constructs a newly allocated <code>ATM</code> object with the specified
     * card number of type <code>long</code>, and with default values of
     * other instance variables.
     *
     * @param cardNum      the <code>long</code> card number
     */
    public ATM(long cardNum) throws CardNotFoundException {
        this.card = Bank.getCardClone(cardNum);
        if(card == null) throw new CardNotFoundException();
        tries = 0;
        this.pinCodeChecked = false;
    }

    /**
     * Returns the number of tries.
     *
     * @return      the <code>int</code> number of tries
     */
    public int getTries(){ return tries; }

    /**
     * Verifies whether the inserted card is blocked or the number
     * of tries exceeds the maximal number of tries allowed. If not,
     * the value of instance variable responsible for the checked
     * pin code becomes true.
     *
     * @param newPinCodeTry      the <code>short</code> pin code
     */
    public void verifyPinAndCard(short newPinCodeTry) throws CardException {

        if(card.getIsBlocked() || tries >= maxPinCodeTries){
            Bank.blockCard(card.getCardNumber());
            throw new CardIsBlockedException("Your card is blocked." +
                    "To access your card again, please visit to manager.");
        }

        if(card.getPinCode() != newPinCodeTry){
            tries++;
            throw new InvalidPincodeException("Pin code is wrong");
        }
        this.pinCodeChecked = true;
    }
    /**
     * Returns the card balance.
     *
     * @return      the <code>double</code> balance
     */

    public double balanceInquiry()throws CardException {
        if(!pinCodeChecked)
            throw new CardException("Pin code is not checked.");
        return this.card.getBalance();
    }

    /**
     * Returns whether the withdrawal of money was successful or not.
     *
     * @return      <code>boolean</code>
     */
    public boolean withdrawMoney(double money) throws CardException{
        if(!pinCodeChecked)
            throw new CardException("Pin code is not checked.");
       return Bank.withdrawMoney(card.getCardNumber(),money);
    }
}

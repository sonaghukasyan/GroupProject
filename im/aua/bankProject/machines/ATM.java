package im.aua.bankProject.machines;

import im.aua.bankProject.exceptions.CardException;
import im.aua.bankProject.exceptions.CardIsBlockedException;
import im.aua.bankProject.exceptions.CardNotFoundException;
import im.aua.bankProject.exceptions.InvalidPincodeException;
import im.aua.bankProject.bankPrivate.Bank;
import im.aua.bankProject.bankPrivate.Card;

public class ATM {
    private int tries;
    private Card card;
    public static final int maxPinCodeTries = 3;
    private boolean pinCodeChecked;

    public ATM(long cardNum) throws CardNotFoundException {
        this.card = Bank.getCardClone(cardNum);
        if(card == null) throw new CardNotFoundException();
        tries = 0;
        this.pinCodeChecked = false;
    }

    public int getTries(){ return tries; }

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

    public double balanceInquiry()throws CardException {
        if(!pinCodeChecked)
            throw new CardException("Pin code is not checked.");
        return this.card.getBalance();
    }

    public boolean withdrawMoney(double money) throws CardException{
        if(!pinCodeChecked)
            throw new CardException("Pin code is not checked.");
       return Bank.withdrawMoney(card.getCardNumber(),money);
    }
}

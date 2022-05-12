package im.aua.bankProject.machines;

import im.aua.bankProject.exceptions.CardException;
import im.aua.bankProject.exceptions.CardNotFoundException;
import im.aua.bankProject.bankPrivate.Bank;
import im.aua.bankProject.bankPrivate.Card;

public class Telcell {
    public static final double DEBIT_DEDUCTION = 300;
    public static final double CREDIT_DEDUCTION = 150;

    //poxel notenoughfundsexception
    public static double transferMoney(long cardNumber, double money) throws CardException {
        if(money <= 0 || money <= CREDIT_DEDUCTION)
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

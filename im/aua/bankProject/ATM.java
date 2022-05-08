package im.aua.bankProject;

public class ATM {
    private int tries;
    private Card card;
    public static final int maxPinCodeTries = 3;
    private boolean pinCodeChecked;

    public ATM(long cardNum) throws CardNotFoundException {
        this.card = Bank.requestCardData(cardNum);
        if(card == null) throw new CardNotFoundException();
        tries = 0;
        this.pinCodeChecked = false;
    }

    public int getTries(){ return tries; }

    public void verifyPinAndCard(short newPinCodeTry) throws CardException {

        if(card.getIsBlocked() || tries >= maxPinCodeTries){
            card.setBlocked(true);
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
       return this.card.withdrawMoney(money);
    }
}

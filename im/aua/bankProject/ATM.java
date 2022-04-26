package im.aua.bankProject;

public class ATM {
    private int tries;
    private Card card;
    public static final int maxTries = 3;

    public ATM(long cardNum, short pinCode) throws CardException {
        verifyCard(cardNum,pinCode);
        tries = 0;
    }

    public int getTries(){ return tries; }

    private void verifyCard(long cardNum, short pinCode) throws CardException {

        this.card = Bank.requestCardData(cardNum);
        if(card == null) throw new CardNotFoundException();

        if(card.getIsBlocked() || tries == maxTries){
            card.setBlocked(true);
            throw new CardIsBlockedException("Your card is blocked." +
                    "To access your card again, please visit to manager.");
        }

        if(card.getPinCode() != pinCode){
            tries++;
            throw new InvalidPincodeException("Pin code is wrong");
        }
    }

    public double balanceInquiry(){
        return this.card.getBalance();
    }

    public boolean cashWithdrawal(double money){
        if(card.getBalance() >= money){
            double balance = card.getBalance();
            balance -= money;
            card.setBalance(balance);
            return true;
        }
        return false;
    }
}

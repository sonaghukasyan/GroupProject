package im.aua.bankProject.bankPrivate;

public class DebitCard extends Card{
    public DebitCard() {
        super();
    }

    public DebitCard(String cardName, CardType type, short pinCode) {
        super(cardName, type, pinCode);
    }

    public DebitCard(DebitCard card) {
        super(card);
    }

    @Override
    public boolean withdrawMoney(double money) {
        if(getBalance() >= money && money >= 0){
            double balance = getBalance();
            balance -= money;
            setBalance(balance);
            return true;
        }
        return false;
    }
}

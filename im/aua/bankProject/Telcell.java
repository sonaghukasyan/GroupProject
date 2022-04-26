package im.aua.bankProject;

public class Telcell {

    //harc: normala vor cardy senc vercnenq?
    public static void transferMoney(long cardNumber, double money) throws Exception {

        Card card = Bank.requestCardData(cardNumber);
        if(card == null ) throw new Exception("Card not found");// sarqel exception klas u dranov anel
        if(money <= 0) throw new Exception("Not valid amount of money.");

        if(money >= 0){
            double balance = card.getBalance();
            balance += money;
            card.setBalance(balance);
        }
    }
}

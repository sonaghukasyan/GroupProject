package im.aua.bankProject.core.bankPrivate;

import im.aua.bankProject.core.exceptions.CardIsBlockedException;
import java.time.*;
import static java.time.temporal.ChronoUnit.SECONDS;

public class CreditCard extends Card implements Cloneable {

    public static final double MinInterestRate = 1;
    public static final double MinBalance = 50000;
    public static final int UnchargedSecCount = 20;
    public static final int ChargedSecCount = 20;

    private final double lineBalance;
    private double interestRate;
    private double debt;
    private LocalDateTime withdrawDate;
    private boolean isBlackListed;

    public CreditCard(double lineBalance) {
        super();
        this.lineBalance = lineBalance;
        interestRate = MinInterestRate;
        debt = 0;
        setBalance(MinBalance);
        setInterestRate();
        isBlackListed = false;
    }

    public CreditCard(String cardName, CardType type, short pinCode, double lineBalance) {
        super(cardName, type, pinCode);

        if(lineBalance < MinBalance){
            System.out.println("We do not give a credit card with" +
                    " initial balance less than " + MinBalance);
            System.exit(0);
        }
        setBalance(lineBalance);
        debt = 0;
        setInterestRate();
        this.lineBalance = lineBalance;
        isBlackListed = false;
    }

    public CreditCard(CreditCard card) {
        super(card);
        setBalance(card.getBalance());
        this.debt = card.debt;
        this.interestRate = card.interestRate;
        this.withdrawDate = card.withdrawDate;
        this.lineBalance = card.lineBalance;
    }
    public void setIsBlackListed(boolean state){
        this.isBlackListed = state;
    }

    public boolean getIsBlackListed() {return this.isBlackListed;}

    private void setInterestRate(){
        double balance = getBalance();
        if(balance < 100000) interestRate = MinInterestRate + 3;
        else if(balance < 300000) interestRate = MinInterestRate + 2;
        else if(balance < 700000) interestRate = MinInterestRate + 1;
        else interestRate = MinInterestRate;
    }

    @Override
    public boolean transferMoney(double money) throws CardIsBlockedException {
        updateDebtAmount();
        updateBlockState();
        if(super.transferMoney(money)){
            if (debt >= 0 && debt <= money) {
                debt = 0;
            }
            else if(debt >= money){
                debt -= money;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean withdrawMoney(double money) throws CardIsBlockedException {
        updateDebtAmount();

        if(getIsBlocked()) {
            throw new CardIsBlockedException("This card is blocked because of debts");
        }
        double balance = getBalance();
        if(balance > money){
            if(balance == lineBalance){
                withdrawDate = LocalDateTime.now();
                System.out.println("Please note that you have 20 seconds to pay your debt " +
                        "without any additional charge fee");
            }
            setBalance(balance - money);
            debt = lineBalance - getBalance();
            return true;
        }
        return false;
    }

    private void updateBlockState(){
        if(withdrawDate == null) return;
        LocalDateTime now = LocalDateTime.now();
        if(withdrawDate.plusSeconds(UnchargedSecCount + ChargedSecCount).compareTo(now) < 0){
            setBlocked(true);
            isBlackListed = true;
        }
    }

    private void updateDebtAmount(){
        if(withdrawDate == null) return;
        if(isBlackListed) return;

        LocalDateTime now = LocalDateTime.now();
        if(withdrawDate.plusSeconds(UnchargedSecCount).compareTo(now) < 0 ){
          // withdrawDate.plusSeconds(UnchargedSecCount + ChargedSecCount).compareTo(now) > 0){
            debt = lineBalance - getBalance();
            long noOfSecBetween = SECONDS.between(withdrawDate.plusSeconds(UnchargedSecCount), now);
            debt += debt * Math.pow((1 + interestRate/(double)100),noOfSecBetween);
        }
        updateBlockState();
    }

    public String getCardExtracts(){
        if(withdrawDate == null) return "no withdraw date";
        updateDebtAmount();
        LocalDateTime unchargedDeadline = withdrawDate.plusSeconds(UnchargedSecCount);
        LocalDateTime chargedDeadline = withdrawDate.plusSeconds(UnchargedSecCount + ChargedSecCount);
        String deadline;
        String info = "Card number: " + getCardNumber() + "\n" +
                      "Balance: " + getBalance() + "\n" +
                      "Debt: " + debt + "\n" +
                      "Interest rate: " + interestRate + "\n" +
                      "Block state: " + getIsBlocked() + "\n" +
                      "Deadline for paying debt without additional charge: " + unchargedDeadline + "\n" +
                      "Last deadline for paying debt with additional charges : " + chargedDeadline + "\n" +
                      "Please note that if you do not pay your debts until the last deadline your account" +
                      " will be blocked and debt will be doubled.";

        return info;
    }

    public CreditCard clone(){
        return new CreditCard(this);
    }
}

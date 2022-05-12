package im.aua.bankProject.bankPrivate;

import im.aua.bankProject.exceptions.CardIsBlockedException;

import java.time.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class CreditCard extends Card implements Cloneable {

    public static final double MinInterestRate = 5;
    public static final double MinBalance = 50000;
    public static final int UnchargedDayCount = 10;
    public static final int ChargedDayCount = 10;

    private final double lineBalance;
    private double interestRate;
    private double debt;
    private LocalDate withdrawDate;

    public CreditCard(double lineBalance) {
        super();
        this.lineBalance = lineBalance;
        interestRate = MinInterestRate;
        debt = 0;
        setBalance(MinBalance);
        setInterestRate();
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
    }

    public CreditCard(CreditCard card) {
        super(card);
        setBalance(card.getBalance());
        this.debt = card.debt;
        this.interestRate = card.interestRate;
        this.withdrawDate = card.withdrawDate;
        this.lineBalance = card.lineBalance;
    }

    private void setInterestRate(){
        double balance = getBalance();
        if(balance < 100000) interestRate = MinInterestRate + 12;
        else if(balance < 300000) interestRate = MinInterestRate + 10;
        else if(balance < 700000) interestRate = MinInterestRate + 5;
        else interestRate = MinInterestRate;
    }

    @Override
    public boolean transferMoney(double money) throws CardIsBlockedException {
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
        updateBlockState();

        if(getIsBlocked()) {
            throw new CardIsBlockedException("This card is blocked because of debts");
        }
        double balance = getBalance();
        if(balance > money){
            if(balance == lineBalance){
                withdrawDate = LocalDate.now();
                System.out.println("Please note that you have 10 days to pay your debt " +
                        "without any additional charge fee");
            }
            setBalance(balance - money);
            debt = money;
            return true;
        }
        return false;
    }

    private void updateBlockState(){
        if(withdrawDate == null) return;
        LocalDate now = LocalDate.now();
        if(withdrawDate.plusDays(UnchargedDayCount + ChargedDayCount).compareTo(now) > 0){
            setBlocked(true);
            debt *= 2;
        }
    }

    private void updateDebtAmount(){
        if(withdrawDate == null) return;

        LocalDate now = LocalDate.now();
        now = now.plusDays(9);
        if(withdrawDate.plusDays(UnchargedDayCount).compareTo(now) > 0 &&
           withdrawDate.plusDays(UnchargedDayCount + ChargedDayCount).compareTo(now) < 0){

            long noOfDaysBetween = DAYS.between(withdrawDate.plusDays(UnchargedDayCount), now);
            debt = lineBalance - getBalance();
            debt += debt * Math.pow(1 + interestRate/(double)100,noOfDaysBetween);
        }
        updateBlockState();
        System.out.println(debt);
    }

    public String getCardExtract(){
        if(withdrawDate == null) return "no withdraw date";
        updateDebtAmount();
        LocalDate unchargedDeadline = withdrawDate.plusDays(UnchargedDayCount);
        LocalDate chargedDeadline = withdrawDate.plusDays(UnchargedDayCount + ChargedDayCount);
        String deadline;
        String info = "Balance: " + getBalance() + "\n" +
                      "Debt: " + debt + "\n" +
                      "Interest rate: " + interestRate + "\n" +
                      "Block state: " + getIsBlocked() + "\n" +
                      "Deadline for paying debt without additional charge: " + unchargedDeadline + "\n" +
                      "Last deadline for paying debt with additional charges : " + chargedDeadline + "\n" +
                      "Please note that if you do not pay your debts until the last deadline your account" +
                      "will be blocked and debt will be doubled.";

        return info;
    }

    public CreditCard clone(){
        return new CreditCard(this);
    }

   /* public static void main(String[] args) {
        //String cardName, CardType type, short pinCode, double lineBalance
        CreditCard c = new CreditCard("Sona Ghukasyan", CardType.CREDIT, (short)1111, (double) 100000);

       try{
           c.withdrawMoney(5000);
       }
       catch (Exception ex ){
           System.out.println(ex.getMessage());
       }

        System.out.println(c.getCardExtract());

        try{
            c.withdrawMoney(1000);
        }
        catch (Exception ex ){
            System.out.println(ex.getMessage());
        }
        System.out.println(c.getCardExtract());
    }*/
}

package im.aua.bankProject;

import java.time.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class CreditCard extends Card{

    public static double MinInterestRate = 5;
    public static double MinBalance = 50000;
    public static int UnchargedDayCount = 10;
    public static int ChargedDayCount = 10;

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

        if(lineBalance < MinInterestRate){
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
    public boolean transferMoney(double money) {
        if(super.transferMoney(money)){
            debt -= money;
            return true;
        }
        return false;
    }

    @Override
    public boolean withdrawMoney(double money) throws CardIsBlockedException {
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
        if(withdrawDate.plusDays(UnchargedDayCount).compareTo(now) > 0 &&
           withdrawDate.plusDays(UnchargedDayCount + ChargedDayCount).compareTo(now) < 0){

            long noOfDaysBetween = DAYS.between(withdrawDate.plusDays(UnchargedDayCount), now);
            debt = lineBalance - getBalance();
            debt += debt * Math.pow(1 + interestRate/(double)100,noOfDaysBetween);
        }
    }

    public String getCardReport(){
        updateDebtAmount();
        updateBlockState();
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
}

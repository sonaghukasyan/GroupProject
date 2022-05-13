package im.aua.bankProject.core.bankPrivate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Deposit implements Serializable {
    public static final int MINIMUM = 1000000;
    public static final int MAXIMUM = 300000000;
    public static final int[] monthsArray = {1, 3, 6, 12, 18, 24};

    private double initialAmount;
    private int months;
    private int depositNumber;
    private LocalDateTime creationDate;
    private double balance;
    private double interest;

    public Deposit() {
        initialAmount = 0;
        months = 0;
        calculateInterest(months);
        creationDate = LocalDateTime.now();
        balance = 0;
        depositNumber = Bank.generateNewDepositNumber();
    }

    public Deposit(double initialAmount, int months) {
        if (initialAmount >= MINIMUM && initialAmount <= MAXIMUM && isValidMonth(months)) {
            this.initialAmount = initialAmount;
            this.months = months;
            creationDate = LocalDateTime.now();
            balance = initialAmount;
            depositNumber = Bank.generateNewDepositNumber();
            calculateInterest(months);
        }
    }

    public Deposit(Deposit deposit){
        if(deposit == null)
            System.out.println("sxaly stexica");
        this.initialAmount = deposit.initialAmount;
        this.months = deposit.months;
        this.creationDate = deposit.creationDate;
        this.balance = deposit.balance;
        this.depositNumber = deposit.depositNumber;
        calculateInterest(months);
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public int getDepositNumber() {
        return depositNumber;
    }

    public int getMonths() {
        return months;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public void setInitialAmount(double initialDeposit) {
        this.initialAmount = initialDeposit;
    }

    public void setCreationDate(LocalDateTime creationDateMonth) {
        this.creationDate = creationDateMonth;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setDepositNumber(int depositNumber) {
        this.depositNumber = depositNumber;
    }

    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) return false;
        else {
            Deposit deposit = (Deposit) o;
            return Double.compare(deposit.initialAmount, initialAmount) == 0
                    && months == deposit.months
                    && Double.compare(deposit.balance, balance) == 0
                    && Objects.equals(creationDate, deposit.creationDate);
        }
    }

    @Override
    public String toString() {
        updateBalance();
        return "Deposit{" +
                "initialAmount=" + initialAmount +
                ", months=" + months +
                ", depositNumber=" + depositNumber +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                '}';
    }

    private double calculateInterest(int months){
        this.interest = 0;
        switch (months) {
            case 1 : interest = 4.5 / 12; break;
            case 3 : interest = 6.25 / 12; break;
            case 6 : interest = 7.75 / 12; break;
            case 12 : interest = 9.0 / 12; break;
            case 18, 24 : interest = 9.25 / 12; break;
        }
        return this.interest;
    }

    public static double finalDeposit(double depositAmount, int months) {
        double percent = 0;
        switch (months) {
            case 1 : percent = 4.5 / 12; break;
            case 3 : percent = 6.25 / 12; break;
            case 6 : percent = 7.75 / 12; break;
            case 12 : percent = 9.0 / 12; break;
            case 18, 24 : percent = 9.25 / 12; break;
        }
        return depositAmount + months*((depositAmount * percent) / 100);
    }

    public double calculateDeposit(double depositAmount, int months){
        return depositAmount + months*((depositAmount * interest) / 100);
    }

    public static boolean isValidMonth(int months){
        for(int i = 0; i < Deposit.monthsArray.length; i++){
            if(months == Deposit.monthsArray[i]){
                return true;
            }
        }
        return false;
    }

    public double calculateCurrentBalance() {
        LocalDateTime now = LocalDateTime.now();

        ; // arg deposit.getCreationDate()
        long difference = creationDate.until(now, ChronoUnit.SECONDS);

        if (difference >= months) {
            balance = calculateDeposit(initialAmount, months);
        } else {
            balance = calculateDeposit(initialAmount,(int)difference);
        }

        return balance;
    }

    private void updateBalance() {
        this.balance = calculateCurrentBalance();
    }

    public String getDepositExtracts(){
        updateBalance();
        return this.toString() + "\nbalance: " + getBalance();
    }
}
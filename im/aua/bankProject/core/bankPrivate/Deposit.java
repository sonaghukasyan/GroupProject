package im.aua.bankProject.core.bankPrivate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * The <code>Deposit</code> class represents the deposits.
 * It contains two final constants of type <code>int</code> representing the
 * minimum and maximum amount of initial money for the deposit.
 * An object of type <code>Deposit</code> contains two instance variables of type
 * <code>double</code> representing the initial amount of money and the current balance,
 * one instance variable of type <code>int</code> representing the number of months that
 * the deposit is taken for, one instance variable of type <code>Date</code>
 * representing the creation date of the deposit, and one final constant of array
 * type with <code>int</code> base type representing the number of months allowed
 * to take a deposit.
 */

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

    /**
     * Constructs a newly allocated <code>Deposit</code> object with default values of
     * the instance variables.
     */
    public Deposit() {
        initialAmount = 0;
        months = 0;
        calculateInterest(months);
        creationDate = LocalDateTime.now();
        balance = 0;
        depositNumber = Bank.generateNewDepositNumber();
    }

    /**
     * Constructs a newly allocated <code>Deposit</code> object with the specified initial
     * amount of money of type <code>double</code> and the number of months of type  <code>int</code>.
     *
     * @param initialAmount           the <code>double</code> initial money
     * @param months                   the <code>int</code> number of months
     */
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
    /**
     * Constructs a newly allocated <code>Deposit</code> object that
     * represents the same deposit as the specified <code>Deposit</code> object.
     *
     * @param deposit      the <code>Deposit</code> object
     */
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

    public int getDepositNumber() {
        return depositNumber;
    }

    public int getMonths() {
        return months;
    }


    public double getBalance() {
        return balance;
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

    /**
     * Returns the string representation of the deposit
     *
     * @return      the <code>String</code> deposit
     */

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

    /**
     * This method calculates the amount of money at the end of the specified
     * <code>int</code> number of months
     *
     *
     * @param months               the <code>int</code> number of months
     * @return                     the <code>double</code> final amount of money
     */
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
    /**
     * This method checks whether the specified <code>int</code> number
     * of months is valid or not.
     *
     * @param months               the <code>int</code> number of months
     * @return                     <code>boolean</code>
     */
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
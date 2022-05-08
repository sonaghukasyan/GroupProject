package im.aua.bankProject;

import java.util.ArrayList;

public class Deposit {
    public static final int MINIMUM = 1000000;
    public static final int MAXIMUM = 300000000;
    public double initialDeposit;
    public int months;
    //avelacnel array months final static

    public Deposit() {
        initialDeposit = 0;
        months = 0;
    }

    public Deposit(double initialDeposit, int months){
        this.initialDeposit = initialDeposit;
        this.months = months;
    }

    public Deposit(Deposit deposit){
        this.initialDeposit = deposit.initialDeposit;
        this.months = deposit.months;
    }

    public double getInitialDeposit() {
        return initialDeposit;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public void setInitialDeposit(double initialDeposit) {
        this.initialDeposit = initialDeposit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Deposit deposit = (Deposit) o;
        return Double.compare(deposit.initialDeposit, initialDeposit) == 0 &&
                this.months == deposit.months;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "initialDeposit = " + initialDeposit +
                ", months = " + months +
                '}';
    }

    public static double calculateDeposit(double initialDeposit, int months) {
        double percent = 0;
        switch (months) {
            case 1:
                percent = 4.5 / 12;
                break;
            case 3:
                percent = 6.25 / 12;
                break;
            case 6:
                percent = 7.75 / 12;
                break;
            case 12:
                percent = 9.0 / 12;
                break;
            case 18, 24:
                percent = 9.25 / 12;
                break;
        }
        return initialDeposit + ((initialDeposit * percent) / 100);
    }
}

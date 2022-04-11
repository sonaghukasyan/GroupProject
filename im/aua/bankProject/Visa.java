package im.aua.bankProject;

import java.util.Date;

public class Visa extends Card{
    //constructors
    public Visa() {
        super();
    }
    public Visa(String cardName, String pinCode) {
        super(cardName, pinCode);
    }
    public Visa(Visa visaCard) {
        super(visaCard);
    }

    //accessors
    @Override
    public String getCardName() {
        return super.getCardName();
    }
    @Override
    public long getCardNumber() {
        return super.getCardNumber();
    }
    @Override
    public String getPinCode() {
        return super.getPinCode();
    }
    @Override
    public boolean getIsBlocked() {
        return super.getIsBlocked();
    }
    @Override
    public Date getExpireDate() {
        return super.getExpireDate();
    }
    @Override
    public double getBalance() {
        return super.getBalance();
    }

    //mutators
    @Override
    public void setCardName(String cardName) {
        super.setCardName(cardName);
    }
    @Override
    public void setCardNumber(long cardNumber) {
        super.setCardNumber(cardNumber);
    }
    @Override
    public void setPinCode(String pinCode) {
        super.setPinCode(pinCode);
    }
    @Override
    public void setBalance(double balance) {
        super.setBalance(balance);
    }
    @Override
    public void setBlocked(boolean blocked) {
        super.setBlocked(blocked);
    }
    @Override
    public void setExpireDate(Date expireDate) {
        super.setExpireDate(expireDate);
    }

    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

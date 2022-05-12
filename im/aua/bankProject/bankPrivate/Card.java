package im.aua.bankProject.bankPrivate;

import im.aua.bankProject.exceptions.CardIsBlockedException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Card implements Serializable, Cloneable {

    public enum CardType{
        DEBIT,
        CREDIT
    }
    private CardType type;
    private String cardName;
    private short pinCode;
    private long cardNumber;
    private LocalDate expireDate;
    private boolean isBlocked;
    private double balance;

    public Card(){
        cardName = "No name";
        pinCode =  0;
        cardNumber = 0;
        expireDate = LocalDate.now();
        expireDate = expireDate.plusYears(2);
        isBlocked = false;
        type = CardType.DEBIT;
    }

    public Card(String cardName, CardType type, short pinCode){

        if(cardName == null){
            System.out.println("Card name or pin code is null");
            System.exit(0);
        }
        this.type = type;
        this.cardName = cardName;
        this.pinCode = pinCode;
        this.cardNumber = Bank.generateNewCardNumber();
        this.isBlocked = false;
        expireDate = LocalDate.now();
        expireDate = expireDate.plusYears(2);
    }

    public Card(Card card)
    {
        if(card == null) {
            System.out.println("Card is null");
            System.exit(0);
        }
        this.type = card.type;
        this.cardName = card.cardName;
        this.pinCode = card.pinCode;
        this.cardNumber = card.cardNumber;
        this.isBlocked = card.isBlocked;
        this.expireDate = card.expireDate;
    }

    public long getCardNumber(){
        return this.cardNumber;
    }

    public short getPinCode(){
        return this.pinCode;
    }

    public String getCardName() {
        return this.cardName;
    }

    public LocalDate getExpireDate(){
        return this.expireDate;
    }

    public double getBalance() {
        return balance;
    }

    public boolean getIsBlocked() {
        return this.isBlocked;
    }

    public CardType getCardType(){ return this.type; }

    //mutators
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPinCode(short pinCode) {
        this.pinCode = pinCode;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    void setBalance(double balance) {
        if(balance >= 0)
           this.balance = balance;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public String toString() {

        return "Card{" +
                "cardName='" + cardName + '\'' +
                ", cardNumber=" + cardNumber +
                ", expireDate=" + expireDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this.getClass() == o.getClass()) {
            Card card = (Card) o;
            return cardNumber == card.cardNumber &&
                    isBlocked == card.isBlocked &&
                    Double.compare(card.balance, balance) == 0 &&
                    cardName.equals(card.cardName) &&
                    pinCode == card.pinCode &&
                    expireDate.equals(card.expireDate);
        }
        else return false;
    }

    public static boolean isValidPinCode(short pinCode){
        int count = 0;
        while(pinCode > 0){
            count++;
            pinCode /= 10;
        }
        return count <= 4;
    }

    public abstract String getCardExtracts();
    public abstract boolean withdrawMoney(double money) throws CardIsBlockedException;

    public boolean transferMoney(double money) throws CardIsBlockedException {
        if(getIsBlocked())
            throw new CardIsBlockedException("This card is blocked." +
                    "and hence you cannot transfer money");

        if(money >= 0){
            balance += money;
            return true;
        }
        return false;
    }

    public Card clone(){
        try {
            Card copy = (Card) super.clone();
            return copy;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

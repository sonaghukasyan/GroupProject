package im.aua.bankProject;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

public abstract class Card {

    private String cardName;
    private String pinCode;
    private long cardNumber;
    private Date expireDate;
    private boolean isBlocked;
    private double balance;

    //add mutators and accessors, toString(), equals()
    public Card(){
        cardName = "No name";
        pinCode =  "no pin code";
        cardNumber = 0;
        expireDate = new Date(Year.now().getValue() + 2, Calendar.FEBRUARY, 1);
        isBlocked = false;
    }

    public Card(String cardName, String pinCode){

        if(cardName == null || pinCode == null){
            System.out.println("Card name or pin code is null");
            System.exit(0);
        }
        this.cardName = cardName;
        this.pinCode = pinCode;
        this.cardNumber = generateNewCardNumber(new Card[]{});
        this.isBlocked = false;
        this.expireDate = new Date( Calendar.getInstance().get(Calendar.YEAR) + 2, Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));
    }

    public Card(Card card)
    {
        if(card == null) {
            System.out.println("Card is null");
            System.exit(0);
        }
        this.cardName = card.cardName;
        this.pinCode = card.pinCode;
        this.cardNumber = card.cardNumber;
        this.isBlocked = card.isBlocked;
        this.expireDate = card.expireDate;
    }

    public long getCardNumber(){
        return this.cardNumber;
    }

    public String getPinCode(){
        return this.pinCode;
    }

    public String getCardName() {
        return this.cardName;
    }

    public Date getExpireDate(){
        return new Date(this.expireDate.getYear(),
                this.expireDate.getMonth(),
                this.expireDate.getDay());
    }

    public double getBalance() {
        return balance;
    }

    public boolean getIsBlocked() {
        return this.isBlocked;
    }

    //mutators
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    private long generateNewCardNumber(Card[] allCards){

        Random random = new Random();
        long cardNumber = random.nextLong(1000000000000000l,10000000000000000L);

        for(int i = 0; i < allCards.length; i++){
            if(allCards[i].cardNumber == cardNumber){
                generateNewCardNumber(allCards);
            }
        }
        return cardNumber;
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
                    pinCode.equals(card.pinCode) &&
                    expireDate.equals(card.expireDate);
        }
        else return false;
    }

    public static Card[] appendCard(Card[] arr, Card... p) {
        Card[] append = new Card[arr.length + p.length];
        int index = 0;
        for(int i = 0; i < append.length; i++){
            if(i < arr.length) append[i] = arr[i];
            else{
                append[i] = p[index];
                index++;
            }
        }
        return append;
    }
}

package im.aua.bankProject;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public abstract class Card {

    private String cardName;
    private String pinCode;
    private long cardNumber;
    private Date expireDate;
    private boolean isBlocked;
    private int balance;

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
}

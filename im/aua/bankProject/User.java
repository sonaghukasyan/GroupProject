package im.aua.bankProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class User{
    private ArrayList<Card> cards;
    private String name;
    private String surname;
    private String passportNumber;
    private ArrayList<Deposit> deposits;

    //constructors
    public User(){
        cards = new ArrayList<Card>();
        name = "no name";
        surname = "no surname";
        passportNumber = "no passport number";
    }

    public User(String name, String surname, String passportNumber){
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.cards = new ArrayList<Card>();;
    }

    public User(String name, String surname, String passportNumber, ArrayList<Card> cards){
        this(name,surname,passportNumber);
        this.cards = new ArrayList<Card>();
        //null check for given cards.
        for(Card card: cards){
            this.cards.add(card);
        }
    }

    public User(User user){
        if(user == null) {
            System.out.println("Card is null");
            System.exit(0);
        }
        this.name = user.name;
        this.surname = user.surname;
        this.passportNumber = user.passportNumber;
        this.cards = new ArrayList<Card>();
        //null check for given cards.
        for(Card card: cards){
            this.cards.add(card);
        }
    }

    //add mutators,appendCard,toString,equals, copy, no arg
    //accessors
    public ArrayList<Card> getCards() {
        ArrayList<Card> copyCards = new ArrayList<Card>();
        for(Card card: cards){
            copyCards.add(card.clone());
        }
        return copyCards;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getPassportNumber() {
        return passportNumber;
    }

    //mutators
    public void setName(String name) {
        this.name = name;
    }
    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void addCard(Card card) {
        if(card != null)
           this.cards.add(card.clone());
    }

    public static boolean isPassportValid(String passport){
        return true;
        //logika greq
    }

    public ArrayList<Deposit> getDeposits() {
        ArrayList<Deposit> copyDeposits = new ArrayList<>();
        for(Deposit deposit: deposits) {
            copyDeposits.add(new Deposit(deposit));
        }
        return copyDeposits;
    }
}

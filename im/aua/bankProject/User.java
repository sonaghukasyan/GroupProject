package im.aua.bankProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class User{
    private ArrayList<Card> cards;
    private String name;
    private String surname;
    private String passportNumber; // make passport class, make deposit class and keep Deposit[] array

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
            copyCards.add(card);
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
        //null check?
        this.cards.add(card);
    }

    public static boolean isPassportValid(String password){
        return true;
        //logika greq
    }

    //sxal a equalsy grac?
    /*
    public boolean equals(Object o) {
        if (this.getClass() == o.getClass()) {
            User user = (User) o;
            return Arrays.equals(cards, user.cards) &&
                    name.equals(user.name) &&
                    surname.equals(user.surname) &&
                    passportNumber.equals(user.passportNumber);
        }
        else return false;
    }*/
}

package im.aua.bankProject.core.bankPrivate;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
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
        deposits = new ArrayList<>();
    }

    public User(String name, String surname, String passportNumber){
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.cards = new ArrayList<Card>();
        this.deposits = new ArrayList<>();
    }

    public User(String name, String surname, String passportNumber, ArrayList<Card> cards){
        this(name,surname,passportNumber);
        this.cards = new ArrayList<Card>();
        this.deposits = new ArrayList<>();
        //null check for given cards.
        for(Card card: cards){
            this.cards.add(card);
        }
    }

    public User(User user){
        if(user == null) {
            System.out.println("User cannot be null");
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
        this.deposits = new ArrayList<Deposit>();
        for(Deposit deposit: deposits){
            this.deposits.add(deposit);
        }
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


    //no-modifier as it gives shallow copy
    //only to bank private classes.
    ArrayList<Card> getCards() {
        return cards;
    }

    // no-modifier as it only bank-private classes can add
    //new cards.
     boolean addCard(Card card) {
        if(card != null){
            this.cards.add(card.clone());
            return true;
        }
        return false;
    }

    public static boolean isPassportValid(String passport){
        if (passport.charAt(0) != 'A') return false;
        if (passport.charAt(1) != 'P') return false;
        int number;

        try{
            number = Integer.parseInt(passport.substring(2));
        }
        catch (NumberFormatException e){
            return false;
        }
        return number > 999999 && number < 10000000;
    }

    @Override
    public String toString() {
        return "User{ " +
                "Name: " + name +
                "Surname: " + surname +
                "PassportNumber: " + passportNumber + "}";
    }

    boolean addDeposit(Deposit deposit) {
        if(deposit != null){
            this.deposits.add(new Deposit(deposit));
            return true;
        }
        return false;
    }

    public ArrayList<Deposit> getDeposits() {
        ArrayList<Deposit> copyDeposits = new ArrayList<>();
        if(deposits == null) return null;
        else {
            for (Deposit deposit : deposits) {
                copyDeposits.add(new Deposit(deposit));
            }
            return copyDeposits;
        }
    }
}

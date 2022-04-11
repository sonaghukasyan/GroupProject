package im.aua.bankProject;

import java.util.Arrays;
import java.util.Objects;

public class User implements IUser{
    private Card[] cards;
    private String name;
    private String surname;
    private String passportNumber; // make passport class, make deposit class and keep Deposit[] array

    //constructors
    public User(){
        cards = new Card[0];
        name = "no name";
        surname = "no surname";
        passportNumber = "no passport number";
    }

    public User(String name, String surname, String passportNumber, Card[] cards){
        this(name,surname,passportNumber);
        this.cards = new Card[cards.length];
        for(int i = 0; i < cards.length; i++) {
            this.cards[i] = cards[i];
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
        this.cards = new Card[user.cards.length];
        for(int i = 0; i < user.cards.length; i++) {
            this.cards[i] = user.cards[i];
        }
    }

    //add mutators,appendCard,toString,equals, copy, no arg
    //accessors
    public Card[] getCards() {
        Card[] copyCards = new Card[cards.length];
        for(int i = 0; i < copyCards.length; i++) {
            copyCards[i] = cards[i];
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
    public void setCards(Card[] cards) {
        for(int i = 0; i < cards.length; i++) {
            this.cards[i] = cards[i];
        }
    }

    public User(String name, String surname, String passportNumber){
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
    }

    public static User[] appendUser(User[] arr, User... p) {
        User[] append = new User[arr.length + p.length];
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

    @Override
    public boolean equals(Object o) {
        if (this.getClass() == o.getClass()) {
            User user = (User) o;
            return Arrays.equals(cards, user.cards) &&
                    name.equals(user.name) &&
                    surname.equals(user.surname) &&
                    passportNumber.equals(user.passportNumber);
        }
        else return false;
    }
}

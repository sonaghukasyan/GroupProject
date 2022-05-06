package im.aua.bankProject;

import java.util.ArrayList;
import java.util.Random;

public class Bank{
    private static ArrayList<User> users;
    private static Bank bank;

    private Bank(){
        users = new ArrayList<User>();
    }

    private Bank(User[] users){
        this.users = new ArrayList<User>();
        //add null check for given users
        for(User user: users){
            this.users.add(user);
        }
    }

    private static Bank getOrSetBank(){
        if(bank == null){
            bank = new Bank();
        }
        return bank;
    }

    public static boolean addUser(User user) {
        bank = getOrSetBank();
        if(user != null){
            users.add(new User(user));
            return true;
        }
        return false;
    }

    public static Card requestCardData(long cardNumber){
        bank = getOrSetBank();
        for(User user: users) {
            ArrayList<Card> cards = user.getCards();
            for(Card card: cards){
                if (card.getCardNumber() == cardNumber) {
                    //cardy cloneable sarqel u cloney tal?
                    return card;
                }
            }
        }
        return null;
    }


    public static User requestUserData(String passport){
        bank = bank.getOrSetBank();
        for(User user: users){
            if(user.getPassportNumber().equals(passport))
                return user;
        }
        return null;
    }

    //manageri mot tar
    public static boolean addCard(User user, Card card){
        bank = getOrSetBank();
        if(card != null){
            user.addCard(card);
           return true;
        }
        return false;
    }

    public static long generateNewCardNumber(){
        getOrSetBank();
        Random random = new Random();
        long cardNumber = random.nextLong(1000000000000000l,10000000000000000L);

        for(User user: users) {
            ArrayList<Card> cards = user.getCards();
            for(Card card: cards){
                if(card.getCardNumber() == cardNumber){
                    generateNewCardNumber();
                }
            }
        }
        return cardNumber;
    }

    public boolean removeUser(User user) {
        return false;
    }

    public boolean editUser(String passport) {
        return false;
    }

    public void blockCard(String passport) {

    }
}

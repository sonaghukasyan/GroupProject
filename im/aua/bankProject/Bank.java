package im.aua.bankProject;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private static ArrayList<User> users;
    private static Bank bank;

    private Bank() {
        users = new ArrayList<>();
    }

    private Bank(ArrayList<User> users) {
        Bank.users = new ArrayList<>();
        //add null check for given users
        Bank.users.addAll(users);
    }

    private static Bank getOrSetBank() {
        if (bank == null)
            bank = new Bank();

        return bank;
    }

    public static boolean addUser(User user) {
        bank = getOrSetBank();
        if (user != null) {
            users.add(new User(user));
            return true;
        }
        return false;
    }

    public static Card requestCardData(long cardNumber) {
        bank = getOrSetBank();
        for (User user : users) {
            ArrayList<Card> cards = user.getCards();
            for (Card card : cards) {
                if (card.getCardNumber() == cardNumber) {
                    return card;
                }
            }
        }
        return null;
    }


    public static User requestUserData(String passport) {
        bank = getOrSetBank();
        for (User user : users) {
            if (user.getPassportNumber().equals(passport))
                return user;
        }
        return null;
    }

    public static boolean addCard(User user, Card card) {
        bank = getOrSetBank();
        if (card != null) {
            user.addCard(card);
            return true;
        }
        return false;
    }

    public static long generateNewCardNumber() {
        getOrSetBank();
        Random random = new Random();
        long cardNumber = random.nextLong(1000000000000000L, 10000000000000000L);

        for (User user : users) {
            ArrayList<Card> cards = user.getCards();
            for (Card card : cards) {
                if (card.getCardNumber() == cardNumber) {
                    generateNewCardNumber();
                }
            }
        }
        return cardNumber;
    }

    //user not found exception
    public static boolean cardBelongsToUser (long cardNumber, String passportNumber) throws CardNotFoundException, InvalidPassportException {
        if(!User.isPassportValid(passportNumber))
                throw new InvalidPassportException("Not valid passport number");

        User user = requestUserData(passportNumber);
        if(user == null) return false;

        for(Card card: user.getCards()){
            if (card.getCardNumber() == cardNumber){
                return true;
            }
        }
        return false;
    }
}

package im.aua.bankProject.bankPrivate;

import im.aua.bankProject.*;
import im.aua.bankProject.exceptions.CardIsBlockedException;
import im.aua.bankProject.exceptions.CardNotFoundException;
import im.aua.bankProject.exceptions.InvalidPassportException;

import java.util.ArrayList;
import java.util.Random;

public class Bank {
    //this field is to give data only to the manager that
    //has the same authentication code.
    private static final String authenticationCode = "AD55_aks_LAD6854SD2";

    private BankDatabase database;
    private ArrayList<User> users;
    //karoxa darna new Bank()?
    private static Bank bank = new Bank();

    private Bank() {
        this.database = new BankDatabase();
        this.users = database.read();
    }

    private static Bank getOrSetBank() {
        if (bank == null)
            bank = new Bank();
        return bank;
    }

    //Only manager can ask to add new User via authentication code
    //and bank rely on the fact that manager will pass valid users.
    public static boolean addUser(User user, String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Wrong authentication code.");

        bank = getOrSetBank();
        if (user != null) {
            bank.users.add(new User(user));
            bank.database.save(bank.users);
            return true;
        }
        return false;
    }

    public static User requestUserData(String passport,String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Your authentication code is wrong");

        bank = getOrSetBank();
        for (User user : bank.users) {
            if (user.getPassportNumber().equals(passport))
                return user;
        }
        return null;
    }

    public static Card.CardType verifyCardAndGetType(long cardNumber){
        bank = getOrSetBank();
        Card card = bank.findCard(cardNumber);
        if(bank.findCard(cardNumber) == null)
            return null;
        return card.getCardType();
    }

    public static Card getCardClone(long cardNumber){
        Card card = bank.findCard(cardNumber);
        if(card == null)
             return null;
        return card.clone();
    }

    public static double transferMoney(long cardNumber,double money) throws CardIsBlockedException {
        bank = getOrSetBank();
        Card card = bank.findCard(cardNumber);
        if(card != null){
            card.transferMoney(money);
            bank.database.save(bank.users);
        }
        return card.getBalance();
    }

    public static Card requestCardData(long cardNumber,String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Your authentication code is wrong");

        bank = getOrSetBank();
        return bank.findCard(cardNumber);
    }

    public static boolean cardBelongsToUser(long cardNumber,String passport, String code) throws Exception {
        User user = Bank.requestUserData(passport,code);
        if(user == null)
            return false;

        ArrayList<Card> cards = user.getCards();
        for (Card card : cards) {
            if (card.getCardNumber() == cardNumber) {
                return true;
            }
        }
        return false;
    }

    public static void blockCard(long cardNumber){
        bank = getOrSetBank();
        Card card = bank.findCard(cardNumber);
        if(card != null){
            card.setBlocked(true);
            bank.database.save(bank.users);
        }
    }

    private Card findCard(long cardNumber){
        for (User user : bank.users) {
            ArrayList<Card> cards = user.getCards();
            for (Card card : cards) {
                if (card.getCardNumber() == cardNumber) {
                    return card;
                }
            }
        }
        return null;
    }

    public static boolean withdrawMoney(long cardNumber, double money) throws CardIsBlockedException {
        bank = getOrSetBank();
        Card card = bank.findCard(cardNumber);
        if(card != null){
            boolean state = card.withdrawMoney(money);
            bank.database.save(bank.users);
            return state;
        }
        return false;
    }

    public static boolean addCard(User user, Card card, String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Wrong authentication code.");

        bank = getOrSetBank();
        if (card != null) {
            user.addCard(card);
            bank.database.save(bank.users);
            return true;
        }
        return false;
    }

    public static long generateNewCardNumber() {
        getOrSetBank();
        Random random = new Random();
        long cardNumber = random.nextLong(1000000000000000L, 10000000000000000L);

        for (User user : bank.users) {
            ArrayList<Card> cards = user.getCards();
            for (Card card : cards) {
                if (card.getCardNumber() == cardNumber) {
                    generateNewCardNumber();
                }
            }
        }
        return cardNumber;
    }

    public static void saveChanges(String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Wrong authentication code.");
        bank.database.save(bank.users);
    }

}

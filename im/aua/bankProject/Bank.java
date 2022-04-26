package im.aua.bankProject;

import java.util.Random;

public class Bank{
    private static IUser[] users;
    private static Bank bank;

    private Bank(){
        bank = new Bank();
        users = new IUser[0];
    }

    private Bank(IUser[] users){
        this.users = new IUser[users.length];
        for(int i = 0; i < users.length;i++){
            this.users[i] = users[i];
        }
    }

    private Bank getOrSetBank(){
        if(bank == null){
            bank = new Bank();
        }
        return bank;
    }

    public static boolean addUser(IUser user) {
        bank = bank.getOrSetBank();
        if(user != null){
            users = User.appendUser(users,new User((User)user));
            return true;
        }
        return false;
    }

    public static Card requestCardData(long cardNumber){
        for(IUser user: users) {
            Card[] cards = ((User) user).getCards();
            for (int i = 0; i < cards.length; i++) {
                if (cards[i].getCardNumber() == cardNumber) {
                    //cardy cloneable sarqel u cloney tal?
                    return cards[i];
                }
            }
        }
        return null;
    }


    public static IUser requestUserData(String passport){
        bank = bank.getOrSetBank();
        for(int i = 0; i < users.length; i++){
            if(((User)users[i]).getPassportNumber() == passport){
                return users[i];
            }
        }
        return null;
    }

    //manageri mot tar
    public static boolean addCard(IUser user, Card card){
        if(card != null){
            Card[] cards = ((User) user).getCards();
            cards = Card.appendCard(cards,card);
            return true;
        }
        return false;
    }

    public static long generateNewCardNumber(){
        Random random = new Random();
        long cardNumber = random.nextLong(1000000000000000l,10000000000000000L);

        for(IUser user: users) {
            Card[] cards = ((User)user).getCards();
            for(int i = 0; i < cards.length; i++){
                if(cards[i].getCardNumber() == cardNumber){
                    generateNewCardNumber();
                }
            }
        }
        return cardNumber;
    }

    public boolean removeUser(IUser user) {
        return false;
    }

    public boolean editUser(String passport) {
        return false;
    }

    public void blockCard(String passport) {

    }
}

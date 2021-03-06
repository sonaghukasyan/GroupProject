package im.aua.bankProject.core.bankPrivate;

import im.aua.bankProject.core.exceptions.CardIsBlockedException;

import java.util.ArrayList;
import java.util.Random;

/**
 * The <code>Bank</code> class represents bank as a storage for its users' data.
 * An object of type <code>Bank</code> contains one instance variable of type
 * <code>ArrayList</code> with base type <code>User</code> containing the users,
 * one instance variable of type <code> Bank </code>.
 */

public class Bank {
    //this field is to give data only to the manager that
    //has the same authentication code.
    private static final String authenticationCode = "AD55_aks_LAD6854SD2";

    private BankDatabase database;
    private ArrayList<User> users;
    private static Bank bank = new Bank();

    private Bank() {
        this.database = new BankDatabase();
        this.users = database.read();
    }
    /**
     * This method adds <code>User</code> user to the bank's users
     * using the specified <code>User</code> object.
     *
     * @param user     the <code>User</code> object
     * @return         <code>boolean</code>
     */
    //Only manager can ask to add new User via authentication code
    //and bank rely on the fact that manager will pass valid users.
    public static boolean addUser(User user, String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Wrong authentication code.");

        if (user != null) {
            bank.users.add(new User(user));
            bank.database.save(bank.users);
            return true;
        }
        return false;
    }

    /**
     * Returns the <code>User</code> user with the specified passport number,
     * checks
     *  authentication code.
     *
     *  @param code     the <code>String</code> code
     * @param passport       the <code>String</code> passport number
     * @return               the <code>Card</code> object
     */

    public static User requestUserData(String passport,String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Your authentication code is wrong");

        for (User user : bank.users) {
            if (user.getPassportNumber().equals(passport))
                return user;
        }
        return null;
    }

    public static Card.CardType verifyCardAndGetType(long cardNumber){
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
        Card card = bank.findCard(cardNumber);
        if(card != null){
            card.transferMoney(money);
            bank.database.save(bank.users);
        }
        return card.getBalance();
    }
    /**
     * Returns the <code>Card</code> card with the specified card number ,checks
     * authentication code.
     *
     * @param cardNumber     the <code>long</code> card number
     * @param code           the <code>String</code> code
     * @return               the <code>Card</code> object
     */
    public static Card requestCardData(long cardNumber,String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Your authentication code is wrong");

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
        Card card = bank.findCard(cardNumber);
        if(card != null){
            boolean state = card.withdrawMoney(money);
            bank.database.save(bank.users);
            return state;
        }
        return false;
    }

    /**
     * This method adds <code>Card</code> card to <code>User</code>
     * user's cards using the specified <code>User</code> and <code>Card</code> objects,
     * checks authentication code.
     *
     *@param code           the <code>String</code> code
     * @param user     the <code>User</code> object
     * @param card     the <code>Card</code> object
     * @return         <code>boolean</code>
     */

    public static boolean addCard(User user, Card card, String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Wrong authentication code.");

        if (card != null) {
            user.addCard(card);
            bank.database.save(bank.users);
            return true;
        }
        return false;
    }

    /**
     * Returns a newly generated <code>long</code> card number.
     *
     * @return            a <code>long</code> card number
     */
    public static long generateNewCardNumber() {
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
    /**
     * Returns the <code>Deposit</code> deposit with the specified deposit number,checks
     * authentication code.
     *
     * @param code           the <code>String</code> code
     * @param depositNumber     the <code>int</code> deposit number
     * @return                  the <code>Deposit</code> object
     */
    public static Deposit requestDepositData(int depositNumber,String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Your authentication code is wrong");

        return bank.findDeposit(depositNumber);
    }

    public static boolean depositBelongsToUser(int depositNumber,String passport, String code) throws Exception {
        User user = Bank.requestUserData(passport,code);
        if(user == null)
            return false;

        ArrayList<Deposit> deposits = user.getDeposits();
        for (Deposit deposit : deposits) {
            if (deposit.getDepositNumber() == depositNumber) {
                return true;
            }
        }
        return false;
    }

    private Deposit findDeposit(int depositNumber){
        for (User user : bank.users) {
            ArrayList<Deposit> deposits = user.getDeposits();
            for (Deposit deposit : deposits) {
                if (deposit.getDepositNumber() == depositNumber) {
                    return deposit;
                }
            }
        }
        return null;
    }

    public static boolean addDeposit(User user, Deposit deposit, String code) throws Exception {
        if(code != authenticationCode)
            throw new Exception("Wrong authentication code.");

        if (deposit != null) {
            user.addDeposit(deposit);
            bank.database.save(bank.users);
            return true;
        }
        return false;
    }

    public static int generateNewDepositNumber() {
        Random random = new Random();
        int depositNumber = random.nextInt(1000000, 10000000);
        for (User user : bank.users) {
            ArrayList<Deposit> deposits = user.getDeposits();
            if(deposits != null) {
                for (Deposit deposit : deposits) {
                    if (deposit.getDepositNumber() == depositNumber) {
                        generateNewDepositNumber();
                    }
                }
            }
        }
        return depositNumber;
    }
}

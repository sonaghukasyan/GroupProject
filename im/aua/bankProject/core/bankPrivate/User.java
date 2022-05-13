package im.aua.bankProject.core.bankPrivate;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * The <code>User</code> class represents the users.
 * An object of type <code>User</code> contains one instance variable of type
 * <code>ArrayList</code> with base type <code>Card</code> containing the cards
 * of the user, one instance variable of type <code>String</code> representing the
 * name, one instance variable of type <code>String</code> representing the surname,
 * one instance variable of type <code>String</code> representing the passport number,
 * and one instance variable of type <code>ArrayList</code> with base type <code>Deposit</code>
 * containing the deposits of the user.
 */
public class User implements Serializable {

    private String name;
    private String surname;
    private String passportNumber;
    private ArrayList<Deposit> deposits;
    private ArrayList<Card> cards;


    /**
     * Constructs a newly allocated <code>User</code> object with default values of
     * the instance variables.
     */

    //constructors
    public User(){
        cards = new ArrayList<Card>();
        name = "no name";
        surname = "no surname";
        passportNumber = "no passport number";
        deposits = new ArrayList<>();
    }

    /**
     * Constructs a newly allocated <code>User</code> object with the specified
     * name, surname and passport number of type <code>String</code>.
     *
     * @param name                 the <code>String</code> name
     * @param surname              the <code>String</code> surname
     * @param passportNumber       the <code>String</code> passport number
     */

    public User(String name, String surname, String passportNumber){
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
        this.cards = new ArrayList<Card>();
        this.deposits = new ArrayList<>();
    }

    /**
     * Constructs a newly allocated <code>User</code> object with the specified
     * name, surname and passport number of type <code>String</code>, and
     * <code>ArrayList</code> with base type <code>Card</code> containing the cards.
     * .
     *
     * @param name                 the <code>String</code> name
     * @param surname              the <code>String</code> surname
     * @param passportNumber       the <code>String</code> passport number
     * @param cards                the <code>ArrayList</code> of cards with base type <code>Card</code>
     */

    public User(String name, String surname, String passportNumber, ArrayList<Card> cards){
        this(name,surname,passportNumber);
        this.cards = new ArrayList<Card>();
        this.deposits = new ArrayList<>();
        //null check for given cards.
        for(Card card: cards){
            this.cards.add(card);
        }
    }

    /**
     * Constructs a newly allocated <code>User</code> object that
     * represents the same user as the specified <code>User</code> user.
     *
     * @param user      the <code>User</code> object
     */
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
    /**
     * Returns the name.
     *
     * @return      the <code>String</code> name
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the surname.
     *
     * @return      the <code>String</code> surname
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Returns the passport number.
     *
     * @return      the <code>String</code> passport number
     */
    public String getPassportNumber() {
        return passportNumber;
    }


    /**
     * Returns the <code>ArrayList</code> of cards with base type <code>Card</code>.
     *
     * @return      the <code>ArrayList</code> of cards with base type <code>Card</code>
     */

    //no-modifier as it gives shallow copy
    //only to bank private classes.
    ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * This method adds the specified <code>Card</code> card to this user's cards.
     *
     * @param card     the <code>Card</code> object
     */
    // no-modifier as it only bank-private classes can add
    //new cards.
     boolean addCard(Card card) {
        if(card != null){
            this.cards.add(card.clone());
            return true;
        }
        return false;
    }
    /**
     * Checks whether the specified <code>String</code> passport number is valid or not.
     *
     * @param passport     the <code>String</code> passport number
     * @return             <code>boolean</code>
     */
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

    /**
     * This method adds the specified <code>Deposit</code> deposit to this user's deposits.
     *
     * @param deposit     the <code>Deposit</code> object
     */
    boolean addDeposit(Deposit deposit) {
        if(deposit != null){
            this.deposits.add(new Deposit(deposit));
            return true;
        }
        return false;
    }
    /**
     * Returns the <code>ArrayList</code> of deposits with base type <code>Depsoit</code>.
     *
     * @return      the <code>ArrayList</code> of deposits with base type <code>Deposit</code>
     */
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

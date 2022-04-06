package im.aua.bankProject;

public class User implements IUser{
    private Card[] cards;
    private String name;
    private String surname;
    private String passportNumber; // make passport class, make deposit class and keep Deposit[] array

    //add mutators,appendCard,toString,equals, copy, no arg
    public User(String name, String surname, String passportNumber, Card[] cards){
        this(name,surname,passportNumber);
        this.cards = new Card[cards.length];
        for(int i = 0; i < cards.length; i++) {
            this.cards[i] = cards[i];
        }
    }

    public User(String name, String surname, String passportNumber){
        this.name = name;
        this.surname = surname;
        this.passportNumber = passportNumber;
    }
}

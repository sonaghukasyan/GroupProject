package im.aua.bankProject;

import java.util.Date;

public class Visa extends Card{
    //constructors
    public Visa() {
        super();
    }
    public Visa(String cardName,CardType type, short pinCode) {
        super(cardName,type, pinCode);
    }
    public Visa(Visa visaCard) {
        super(visaCard);
    }
}

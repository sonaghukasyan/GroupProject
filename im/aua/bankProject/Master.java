package im.aua.bankProject;

import java.util.Date;

public class Master extends Card{
    public Master() {
        super();
    }
    public Master(String cardName,CardType type, short pinCode) {
        super(cardName, type, pinCode);
    }
    public Master(Visa visaCard) {
        super(visaCard);
    }
}



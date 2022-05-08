package im.aua.bankProject;

public class Manager {
    private User user;

    public static void addUser(User user) throws InvalidPassportException {
        if(!User.isPassportValid(user.getPassportNumber())){
            throw new InvalidPassportException("Invalid passport.");
        }
        Bank.addUser(user);
    }

    public static User verifyUser(String passport) throws InvalidPassportException {
        if (!User.isPassportValid(passport)) {
            throw new InvalidPassportException("Invalid passport.");
        }
        return Bank.requestUserData(passport);
    }

    public static boolean AddCard(User user, Card card){
        User theUser;
        try{
            theUser = verifyUser(user.getPassportNumber());
        }
        catch (InvalidPassportException e) {
            return false;
        }
        if(!Card.isValidPinCode(card.getPinCode())) return false;

        Bank.addCard(user,card);
        return true;
    }

    public static void unblockCard(){

    }

    public static boolean addDeposit(){
        return false;
        //avelacreq logika
    }
}

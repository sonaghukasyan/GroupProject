package im.aua.bankProject;

import im.aua.bankProject.bankPrivate.Bank;
import im.aua.bankProject.bankPrivate.Card;
import im.aua.bankProject.bankPrivate.User;
import im.aua.bankProject.exceptions.InvalidPassportException;

public class Manager {

    private static final String authenticationCode = "AD55_aks_LAD6854SD2";

    public static void addUser(User user) throws InvalidPassportException {
        if(user != null){
            if(!User.isPassportValid(user.getPassportNumber())){
                throw new InvalidPassportException("Invalid passport.");
            }
            try{
                Bank.addUser(user,authenticationCode);
            } catch (Exception ignored) {}
        }
    }

    public static User getUserClone(String passport) throws InvalidPassportException {
        if (!User.isPassportValid(passport)) {
            throw new InvalidPassportException("Invalid passport.");
        }

        try{
            User user = Bank.requestUserData(passport,authenticationCode);
            if(user != null) {
                return new User(user);
            }
        } catch (Exception ignored) {}

        return null;
    }

    public static boolean AddCard(String passport, Card card){
        if(!User.isPassportValid(passport)) return false;
        if(!Card.isValidPinCode(card.getPinCode())) return false;

        try{
            User user = Bank.requestUserData(passport,authenticationCode);
            if (user == null) return false;
            Bank.addCard(user,card, authenticationCode);
            return true;
        } catch (Exception ignored) {}

        return false;
    }

    public static void unblockCard(){

    }

    public static boolean addDeposit(){
        return false;
    }
}

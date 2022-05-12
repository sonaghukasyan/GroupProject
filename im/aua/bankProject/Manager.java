package im.aua.bankProject;

import im.aua.bankProject.bankPrivate.*;
import im.aua.bankProject.exceptions.CardIsBlockedException;
import im.aua.bankProject.exceptions.CardNotFoundException;
import im.aua.bankProject.exceptions.InvalidPassportException;
import im.aua.bankProject.exceptions.InvalidPincodeException;

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

    public static String seeExtracts(long cardNumber,short pinCode) throws Exception {
        Card card = Bank.requestCardData(cardNumber,authenticationCode);

        if(card == null)
            throw new CardNotFoundException();
        if(card.getPinCode() != pinCode)
            throw new InvalidPincodeException();
        String info = card.getCardExtracts();
        Bank.saveChanges(authenticationCode);
        return info;
    }

    public static boolean cardBelongsToUser(long cardNumber, String passport) {
        try{
            return Bank.cardBelongsToUser(cardNumber, passport,authenticationCode);
        }
        catch (Exception ignored) {}
        return false;
    }

    public static boolean unblockCard(long cardNumber, short newPincode) throws Exception {
        Card card = unblock(cardNumber);

        if(card == null || !Card.isValidPinCode(newPincode))
            return false;

        card.setPinCode(newPincode);
        return true;
    }

    public static boolean unblockCard(long cardNumber) throws Exception {
        if(unblock(cardNumber) != null)
            return true;
        return false;
    }

    private static Card unblock(long cardNumber) throws Exception {
        Card card = Bank.requestCardData(cardNumber,authenticationCode);
        if(card == null )
            return null;

        if(card.getCardType() == Card.CardType.CREDIT){
            CreditCard credit = (CreditCard)card;
            if(credit.getIsBlackListed())
                throw new CardIsBlockedException("Your card is in a black list " +
                        " you cannot unblock it. Your debt will be paid after the trial ");
        }

        card.setBlocked(false);
        return card;

    }

    public static boolean addDeposit(){
        return false;
    }
}

package im.aua.bankProject;

import im.aua.bankProject.bankPrivate.Card;
import im.aua.bankProject.bankPrivate.CreditCard;
import im.aua.bankProject.bankPrivate.DebitCard;
import im.aua.bankProject.bankPrivate.User;
import im.aua.bankProject.exceptions.*;
import im.aua.bankProject.machines.ATM;
import im.aua.bankProject.machines.Telcell;

import java.util.Scanner;

public class UI{
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Write corresponding number.");
        System.out.println("1.Visit Manager  2.Use ATM  3.Use TelCell");
        int value = scanner.nextInt();

        switch (value){
            case 1:
                clearConsole();
                visitManager();
                start();
                break;
            case 2:
                useATM();
                start();
                break;
            case 3:
                useTelCell();
                start();
                break;
            default:
                System.out.println("Invalid option");
                start();
                break;
        }
    }

    public void useATM() {
        ATM atm = createATM();
        System.out.println("1.Balance Inquiry   2.Cash Withdrawal");
        int value = scanner.nextInt();

        try{
            switch (value) {
                case 1:
                    System.out.println(atm.balanceInquiry());
                    break;
                case 2:
                    System.out.println("Withdrawal amount: ");
                    double money = scanner.nextDouble();

                    if (atm.withdrawMoney(money)) {
                        System.out.println("Get your money.");
                    } else {
                        System.out.println("Not enough funds");
                    }
                    System.out.println("Get your card!");
                    start();
                    break;

                default:
                    System.out.println("Invalid option");
                    start();
                    break;
            }
        }
        catch(CardException ex){
            System.out.println(ex.getMessage());
            System.exit(0);
        }
    }

    public ATM createATM(){
        System.out.println("Write card number: ");
        long cardNumber = scanner.nextLong();

        ATM atm;
        try{
            atm = new ATM(cardNumber);
        }
        catch (CardNotFoundException ex){
            System.out.println(ex.getMessage());
            atm = null;
            start();
        }

        while(atm.getTries() <= ATM.maxPinCodeTries) {
            try {
                System.out.println("Write pin code: ");
                short pinCode = scanner.nextShort();
                atm.verifyPinAndCard(pinCode);
                break;
            }
            catch (CardIsBlockedException ex){
                System.out.println(ex.getMessage());
                start();
            }
            catch (InvalidPincodeException ex){
                System.out.println(ex.getMessage());
            }
            catch(CardException ex){
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        }
        return atm;
    }

    public void useTelCell() {
        System.out.println("Write card number: ");
        long cardNumber = scanner.nextLong();
        System.out.println("Insert money: ");
        double money = scanner.nextDouble();

        System.out.println("Please note that telCell will deduct " +
                Telcell.CREDIT_DEDUCTION + " drams for credit card and " +
                Telcell.DEBIT_DEDUCTION + " drams for debit card transfers.\n" +
                "Do you want to continue? : 1.Yes  2.No ");
        int answer = scanner.nextInt();
        if(answer == 1){
            try{
                System.out.println("Your balance: " +
                        Telcell.transferMoney(cardNumber,money));
            }
            catch (CardException ex){
                System.out.println(ex.getMessage());
            }
        }
        else
            start();
    }

    public void seeExtracts(){
        System.out.println("Write card number: ");
        long cardNumber = scanner.nextLong();

        System.out.println("Write pin-code: ");
        short pinCode = scanner.nextShort();

        try{
            System.out.println(Manager.seeExtracts(cardNumber,pinCode));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            start();
        }
    }

    public void visitManager() {
        System.out.println("Write corresponding letter.");
        System.out.println("1.Add user  2.Create new Card  3.Add deposit  " +
                "4.Unblock card  5.See card extracts");
        int value = scanner.nextInt();

        switch (value){
            case 1:
                createUser();
                visitManager();
                break;
            case 2:
                createCard();
                break;
            case 3:
                //addDeposit();
                break;
            case 4:
                unblockCard();
                break;
            case 5:
                seeExtracts();
                break;
            default:
                System.out.println("Invalid letter");
                start();
                break;
        }
    }


    public User createUser() {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Surname: ");
        String surname = scanner.next();

        System.out.print("Passport: ");
        String passport = scanner.next();

        User user = new User(name,surname,passport);
        try{
            Manager.addUser(user);
        } catch (InvalidPassportException ex) {
            System.out.println(ex.getMessage());
            start();
        }
        System.out.println("User is created!");
        return user;
    }

    public User getUser(){
        System.out.println("Write corresponding number.");
        System.out.println("Are you registered?  1.Yes  2.No");
        int value = scanner.nextInt();

        switch (value){
            case 2:
                return createUser();
            case 1:
                System.out.print("Passport: ");
                String passport = scanner.next();
                try{
                    User user = Manager.getUserClone(passport);
                    if(user == null){
                        System.out.println("Not valid user, please create account");
                        return createUser();
                    }
                    return user;

                } catch (InvalidPassportException e) {
                    System.out.println(e.getMessage());
                    getUser();
                }
            default:
                System.out.println("Invalid option, try again.");
                return getUser();
        }
    }

    public void createCard() {
        User user = getUser();
        String cardName = user.getName() + " " + user.getSurname();

        System.out.println("Write 4-digit pin code");
        short pincode;
        while(true){
            pincode = scanner.nextShort();
            if(!Card.isValidPinCode(pincode)){
                System.out.println("Not valid pin code, try again.");
                continue;
            }
            break;
        }

        System.out.println("Card Type: 1.Debit Card 2.Credit Card");
        int value = scanner.nextInt();

        Card.CardType type;
        boolean state = true;
        Card card = null;

        while(state){
            switch (value){
                case 1: type = Card.CardType.DEBIT;
                    card = new DebitCard(cardName,type,pincode);
                    state = false;
                    break;
                case 2: type = Card.CardType.CREDIT;
                    System.out.print("Credit card loan money amount: ");
                    double lineBalance = scanner.nextDouble();
                    card = new CreditCard(cardName,type,pincode,lineBalance);
                    state = false;
                    break;
                default: System.out.println("Invalid option, try again");
                    break;
            }
        }
        if(Manager.AddCard(user.getPassportNumber(),card))
                  System.out.println("Your card info: \n" + card);

    }

    public void unblockCard() {

        System.out.print("Passport number: ");
        String passportNumber = scanner.next();

        System.out.print("Card number: ");
        long cardNumber = scanner.nextLong();

        if(!Manager.cardBelongsToUser(cardNumber, passportNumber)){
           System.out.println("User not found or/and user does not have " +
                   "that card");
           start();
        }

        System.out.println("Do you want to reset your pin code?");
        System.out.println("1. Yes  2. No");

        int answer = scanner.nextInt();

        switch (answer) {
            case 1:
                System.out.println("Enter new 4-digit pin code");
                short pinCode = scanner.nextShort();
                try{
                    Manager.unblockCard(cardNumber);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                try{
                    Manager.unblockCard(cardNumber);
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
               break;
            default: System.out.println("Invalid option, try again");
        }
        System.out.println("Your card is unblocked.");
    }


    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

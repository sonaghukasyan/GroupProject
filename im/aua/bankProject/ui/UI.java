package im.aua.bankProject.ui;

import im.aua.bankProject.core.bankPrivate.*;
import im.aua.bankProject.core.Manager;
import im.aua.bankProject.core.exceptions.*;
import im.aua.bankProject.core.machines.ATM;
import im.aua.bankProject.core.machines.Telcell;

import java.util.Arrays;
import java.util.Scanner;

import static im.aua.bankProject.core.bankPrivate.Deposit.MAXIMUM;
import static im.aua.bankProject.core.bankPrivate.Deposit.MINIMUM;

public class UI{
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Write corresponding number.");
        System.out.println("1.Visit Manager  2.Use ATM  3.Use TelCell");
        int value = scanner.nextInt();

        switch (value){
            case 1:
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
                "4.Unblock card  5.See card extracts 6.See deposit");
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
                addDeposit();
                break;
            case 4:
                unblockCard();
                break;
            case 5:
                seeExtracts();
                break;
            case 6:
                seeDeposit();
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
                    Manager.unblockCard(cardNumber,pinCode);
                    System.out.println("Your card is unblocked.");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            case 2:
                try{
                    Manager.unblockCard(cardNumber);
                    System.out.println("Your card is unblocked.");
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                }
               break;
            default: System.out.println("Invalid option, try again");
        }
    }

    public void addDeposit() {
        User user = getUser();

        System.out.println("You may get a deposit in amount of " + MINIMUM + "-"+
                MAXIMUM +  "AMD");
        boolean flag = true;
        while (flag) {
            System.out.println("How much initial deposit do you want?");

            double initialDeposit = scanner.nextDouble();

            System.out.println("For how many months do you want to keep the deposit?");
            System.out.println(Arrays.toString(Deposit.monthsArray));

            int months = scanner.nextInt();
            makeDeposit(initialDeposit,months);

            if(!Deposit.isValidMonth(months)) return;

            System.out.println("\nDo you want to confirm this deposit?"); //buttons
            System.out.println("1. Yes  2. No");

            int n = scanner.nextInt();

            switch (n) {
                case 1:
                    Deposit deposit = new Deposit(initialDeposit, months);
                    Manager.addDeposit(user.getPassportNumber(), deposit);
                    System.out.println(deposit);
                    flag = false;
                    break;
                case 2:
                    System.out.println("Do you want to try another deposit conditions.");
                    System.out.println("1. Yes  2. No");

                    int m = scanner.nextInt();

                    switch (m) {
                        case 1 : flag = true; break;
                        case 2 : flag = false; break;
                        default : System.out.println("Invalid option, try again");
                                  start();
                                  break;
                    }
                    break;
                default: System.out.println("Invalid option, try again");
                    break;
            }
        }
    }

    public void seeDeposit() {
        System.out.println("Write passport number: ");
        String passportNumber = scanner.next();

        System.out.println("Write deposit number: ");
        int depositNumber = scanner.nextInt();

        try{
            System.out.println(Manager.seeDeposit(depositNumber, passportNumber));
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            start();
        }
    }

    public void makeDeposit(double depositAmount, int months) {
        if(!(depositAmount >= MINIMUM && depositAmount <= MAXIMUM)){
            System.out.println("The bank does not give a deposit for that amount of money.");
            start();
        }
        if(!(Deposit.isValidMonth(months))){
            System.out.println("The bank does not give a deposit for that amount of months.");
            start();
        }

        double balance = Deposit.finalDeposit(depositAmount, months);

        System.out.printf("Deposit is successfully approved." +"\n You can take your balance after " + months +
                " months. " + "It will be " + "%.2f", balance);
    }
}

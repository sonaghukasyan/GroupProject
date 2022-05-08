package im.aua.bankProject;

import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.StreamSupport;

public class UI{
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Write corresponding number.");
        System.out.println("1.Visit Manager  2.Use ATM  3.Use TelCell");
        int value = scanner.nextInt();

        switch (value){
            case 1:
                System.out.println("\033[H\033[2J");
                visitManager();
                start();
                break;
            case 2:
                System.out.println("\033[H\033[2J");
                useATM();
                start();
                break;
            case 3:
                System.out.println("\033[H\033[2J");
                useTelCell();
                start();
                break;
            default:
                System.out.println("\033[H\033[2J");
                System.out.println("Invalid option");
                start();
                break;
        }
    }

    public void useATM() {

        ATM atm = createATM();
        System.out.println("\033[H\033[2J");
        System.out.println("1.Balance Inquiry   2.Cash Withdrawal");
        int value = scanner.nextInt();

        try{
            switch (value) {
                case 1:
                    System.out.println("\033[H\033[2J");
                    System.out.println(atm.balanceInquiry());
                    break;
                case 2:
                    System.out.println("\033[H\033[2J");
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
        try{
            Telcell.transferMoney(cardNumber,money);
        }
        catch (Exception ex){
            System.exit(0);
        }
    }

    public void visitManager() {
        System.out.println("Write corresponding letter.");
        System.out.println("1.Add user  2.Create new Card  3.Add deposit  4.Unblock card");
        int value = scanner.nextInt();

        switch (value){
            case 1:
                System.out.println("\033[H\033[2J");
                createUser();
                visitManager();
                break;
            case 2:
                System.out.println("\033[H\033[2J");
                createCard();
                break;
            case 3:
                System.out.println("\033[H\033[2J");
                addDeposit();
                break;
            case 4:
                System.out.println("\033[H\033[2J");
                unblockCard();
            default:
                System.out.println("\033[H\033[2J");
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
                System.out.println("\033[H\033[2J");
                return createUser();
            case 1:
                System.out.print("Passport: ");
                String passport = scanner.next();
                try{
                    User user = Manager.verifyUser(passport);
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
                System.out.println("\033[H\033[2J");
                System.out.println("Invalid option, try again.");
                return getUser();
        }
    }

    public Card createCard() {
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

        System.out.print("Card Type: 1.Debit Card 2.Credit Card");
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
        Manager.AddCard(user,card);
        System.out.println("Your card info: \n" + card.toString());
        return card;
    }

    public void addDeposit() {
        User user = getUser();
        System.out.println("You may get a deposit in amount of " + Deposit.MINIMUM + "-"+
                Deposit.MAXIMUM +  "AMD");
        boolean flag = true;
        while (flag) {
            System.out.println("How much initial deposit do you want?");

            double initialDeposit = scanner.nextDouble();

            System.out.println("For how many months do you want to keep the deposit?");
            System.out.println("1   3   6   12   18   24");  //buttons

            //avelacnel month stugumy
            int months = scanner.nextInt();

            System.out.println("This will be your deposit after " + months + " months.");
            System.out.println(Deposit.calculateDeposit(initialDeposit, months));
            System.out.println("Do you want to confirm this deposit?"); //buttons
            System.out.println("1. Yes  2. No");

            int n = scanner.nextInt();

            switch (n) {
                case 1:
                    Deposit deposit = new Deposit(initialDeposit, months);
                    //Managerov petqa avelana
                    //Bank.addDeposit(user, deposit);
                    flag = false;
                    break;
                case 2:
                    System.out.println("Do you want to try another deposit conditions.");
                    System.out.println("1. Yes  2. No");

                    int m = scanner.nextInt();

                    switch (m) {
                        case 1 -> flag = true;
                        case 2 -> flag = false;
                        default -> System.out.println("Invalid option, try again");
                    }
                default: System.out.println("Invalid option, try again");
                    break;
            }
        }
    }

    public void unblockCard() {
        System.out.print("Card number: ");
        long cardNumber = scanner.nextLong();
        System.out.print("Passport number: ");
        String passportNumber = scanner.next();

        boolean flag = false;
        try {
            flag = Bank.cardBelongsToUser(cardNumber, passportNumber);
        } catch (CardNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        if(flag){
            System.out.println("Do you want to reset your pin code?");
            System.out.println("1. Yes  2. No");

            int m = scanner.nextInt();

            switch (m) {
                case 1:
                    System.out.println("Enter new 4-digit pin code");
                    short pinCode = scanner.nextShort();
                    Bank.requestCardData(cardNumber).setPinCode(pinCode);
                    Bank.requestCardData(cardNumber).setBlocked(false);
                case 2:
                    Bank.requestCardData(cardNumber).setBlocked(false);
                default:
                    System.out.println("Invalid option, try again");
            }
            System.out.println("Your card is unblocked.");
        }
    }
}

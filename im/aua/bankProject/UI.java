package im.aua.bankProject;

import java.util.Locale;
import java.util.Scanner;
import java.util.stream.StreamSupport;

public class UI{
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Write corresponding letter.");
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
        System.out.println("Write card number: ");
        long cardNumber = scanner.nextLong();

        System.out.println("Write pin code: ");
        short pinCode = scanner.nextShort();

        ATM atm;
        while(true){
            try{
                atm  = new ATM(cardNumber,pinCode);
                break;
            } catch (CardIsBlockedException ex){
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

        System.out.println("\033[H\033[2J");
        System.out.println("1.Balance Inquiry   2.Cash Withdrawal");
        int value = scanner.nextInt();

        switch (value) {
            case 1:
                System.out.println("\033[H\033[2J");
                System.out.println(atm.balanceInquiry());
                break;
            case 2:
                System.out.println("\033[H\033[2J");
                System.out.println("Withdrawal amount: ");
                double money = scanner.nextDouble();

                try{
                    if (atm.withdrawMoney(money)) {
                        System.out.println("Get your money.");
                    } else {
                        System.out.println("Not enough funds");
                    }
                    System.out.println("Get your card!");
                    start();
                    break;
                }
                catch (CardIsBlockedException ex){
                    System.out.println(ex.getMessage());
                }
            default:
                System.out.println("Invalid option");
                start();
                break;
        }
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
            System.out.println(ex.getMessage());
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
                otherManagerServices();
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

        String passport = "";
        //3 attempts to write valid passport number;
        for(int i = 0; i < 3; i++ ){
            System.out.print("Passport: ");
            passport = scanner.next();
            if(!User.isPassportValid(passport)){
                System.out.println("Invalid passport.Try again ");
                break;
            }
            if(i == 2){
                System.out.println("Invalid passport attempts ended.");
                System.exit(0);
            }
            else{
                break;
            }
        }
        User user = new User(name,surname,passport);
        //petqa manager class unenal u es amboxjy ira mijocov anel?
        Bank.addUser(user);
        System.out.println("User is created!");
        return user;
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
        Bank.addCard(user,card);
        System.out.println(card.toString());
        return card;
    }

    //manager classum avelacru verifyUser sra poxaren
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
                User user = Bank.requestUserData(passport);
                if(user == null){
                    System.out.println("Not valid user, please create account");
                    return createUser();
                }
                return user;
            default:
                System.out.println("\033[H\033[2J");
                System.out.println("Invalid option, try again.");
                return getUser();
        }
    }

    public void edit() {

    }

    public void removeUser() {

    }

    public void addDeposit() {

    }

    public void otherManagerServices() {

    }
}

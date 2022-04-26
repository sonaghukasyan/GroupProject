package im.aua.bankProject;

import java.util.Locale;
import java.util.Scanner;

public class UI implements IUI{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void start() {
        System.out.println("Write corresponding letter.");
        System.out.println("1.Visit Manager  2.Use ATM  3.Use TelCell");
        int value = scanner.nextInt();

        switch (value){
            case 1:
                System.out.println("\033[H\033[2J");
                visitManager();
                break;
            case 2:
                System.out.println("\033[H\033[2J");
                useATM();
                break;
            case 3:
                System.out.println("\033[H\033[2J");
                useTelCell();
                break;
            default:
                System.out.println("\033[H\033[2J");
                System.out.println("Invalid option");
                start();
                break;
        }
    }

    @Override
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
            }
            catch(CardNotFoundException ex){
                System.out.println(ex.getMessage());
                System.exit(0);
            }
            catch (CardIsBlockedException ex){
                System.out.println(ex.getMessage());
                start();
            }
            catch (InvalidPincodeException ex){
                System.out.println(ex.getMessage());
            }
            catch (CardException ex){
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

                if (atm.cashWithdrawal(money)) {
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

    @Override
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

    @Override
    public void visitManager() {
        System.out.println("Write corresponding letter.");
        System.out.println("1.Add user  2.Create new Card  3.Add deposit  4.Unblock card");
        int value = scanner.nextInt();

        switch (value){
            case 1:
                System.out.println("\033[H\033[2J");
                createUser();
                System.out.println("\033[H\033[2J");
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

    @Override
    public IUser createUser() {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Surname: ");
        String surname = scanner.next();

        String passport = "";
        //3 attempts to write valid passport number;
        for(int i = 0; i < 3; i++ ){
            System.out.print("Passport: ");
            passport = scanner.next();
            if(IUser.isPassportValid(passport)){
                System.out.println("Invalid passport.Try again ");
                break;
            }
            if(i == 2){
                System.out.println("Invalid passport attempts ended.");
                System.exit(0);
            }
        }
        IUser user = new User(name,surname,passport);
        //petqa manager class unenal u es amboxjy ira mijocov anel?
        Bank.addUser(user);
        return user;
    }

    @Override
    public Card createCard() {
        IUser user = getUser();
        String cardName = ((User)user).getName() + " " + ((User)user).getSurname();

        System.out.print("Card Type  1.Visa  2.Master");
        int value = scanner.nextInt();

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

        Card.CardType type;
        boolean state = true;
        Card card = null;

        while(state){
            switch (value){
                case 1: type = Card.CardType.VISA;
                        card = new Visa(cardName,type,pincode);
                        state = false;
                        break;
                case 2: type = Card.CardType.MASTER;
                        card = new Master(cardName,type,pincode);
                        state = false;
                        break;
                default: System.out.println("Invalid option, try again");
                         break;
            }
        }
        Bank.addCard(user,card);
        return card;
    }

    //manager classum avelacru verifyUser sra poxaren
    public IUser getUser(){
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
                IUser user = Bank.requestUserData(passport);
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

    @Override
    public void edit() {

    }

    @Override
    public void removeUser() {

    }


    @Override
    public void addDeposit() {

    }

    @Override
    public void otherManagerServices() {

    }
}

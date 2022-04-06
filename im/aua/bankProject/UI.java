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
                System.out.println("Invalid letter");
                start();
                break;
        }
    }

    @Override
    public void useATM() {

    }

    @Override
    public void useTelCell() {

    }

    @Override
    public void visitManager() {
        System.out.println("Write corresponding letter.");
        System.out.println("1.Create new Card  2.Add deposit  3.Other");
        int value = scanner.nextInt();

        switch (value){
            /*case 1:
                System.out.println("\033[H\033[2J");
                createUser();
                break;*/
            case 1:
                System.out.println("\033[H\033[2J");
                createCard();
                break;
            case 2:
                System.out.println("\033[H\033[2J");
                addDeposit();
                break;
            case 3:
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

    }
    @Override
    public void edit() {

    }

    @Override
    public void removeUser() {

    }


    @Override
    public Card createCard() {
        if(!isRegistered()){
            System.out.println("\033[H\033[2J");
            System.out.println("You need to create account firstly.\n" +
                    "1.Create new account  2.Cancel");

            int value = scanner.nextInt();
            switch (value){
                case 1:
                    System.out.println("\033[H\033[2J");
            }

        }
        return new Visa();
    }

    @Override
    public void addDeposit() {

    }

    @Override
    public void otherManagerServices() {

    }

    public IUser RegisterCheck(){
        System.out.println("Write corresponding number.");
        System.out.println("Are you registered?  1.Yes  2.No");
        int value = scanner.nextInt();

        if(value != 1 || value != 2){
            System.out.println("Invalid option");
        }
        else if(value == 2){
            return createUser();
        }

        System.out.println("Write your passport number: ");
        System.out.println("Are you registered?  1.Yes  2.No");
        String passport = scanner.next();

    }
}

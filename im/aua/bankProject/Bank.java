package im.aua.bankProject;

public class Bank {
    private static IUser[] users;
    private static Bank bank;

    private Bank(){
        bank = new Bank();
        users = new IUser[0];
    }
    /*
    private Bank(IUser[] users){

        this.users = new IUser[users.length];
        for(int i = 0; i < users.length;i++){
            this.users[i] = users[i];
        }
    }*/

   /* public static IUser requestUserData(String passport){
        for(int i = 0; i < users.length; i++){

        }
    }*/
}

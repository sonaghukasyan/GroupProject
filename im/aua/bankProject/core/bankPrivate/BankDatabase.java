package im.aua.bankProject.core.bankPrivate;

import java.io.*;
import java.util.ArrayList;

public class BankDatabase {
    //we know that path should be relative, but there was a problem.
    public static String PATH = "C:\\Users\\User\\GroupProject\\database.txt";

    public void save(ArrayList<User> users){
        try
        {
            FileOutputStream fos = new FileOutputStream(PATH);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(users);
            oos.close();
            fos.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error opening/creating the file " + PATH);
            System.exit(0);
        }
        catch (IOException e) {
        }

    }

    public ArrayList<User> read() {

        ArrayList<User> users = null;
        try {
            FileInputStream fis = new FileInputStream(PATH);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = (ArrayList<User>) ois.readObject();
            // down-casting object

            // closing streams
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error opening/creating the file " + PATH);
            System.exit(0);
        } catch (IOException | ClassNotFoundException e) {

        }

        if(users == null)
            return new ArrayList<>();
        return users;
    }
}
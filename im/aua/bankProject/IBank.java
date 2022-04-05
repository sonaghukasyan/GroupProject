package im.aua.bankProject;

public interface IBank {
    boolean addUser(IUser user);
    boolean removeUser(IUser user);
    boolean editUser(String passport);
    void blockCard(String passport);
}

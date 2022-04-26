package im.aua.bankProject;

public interface IBank {
    boolean removeUser(IUser user);
    boolean editUser(String passport);
    void blockCard(String passport);
}

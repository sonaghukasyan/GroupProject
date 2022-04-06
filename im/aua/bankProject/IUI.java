package im.aua.bankProject;

public interface IUI {
    void start();
    void useATM();
    void useTelCell();
    void visitManager();
    void edit();
    void removeUser();
    IUser createUser();
    Card createCard();
    void addDeposit();
    void otherManagerServices();
}

package entities;

public interface IAdministrator extends IUsers {
    public void addRequester();
    public void deleteRequester();
    public void UpdateRequester();
    public void clearDatabase();
    public void resetDatabase();
    public void viewOrders();
    public void viewRequesters();
}

package entities;


import java.util.ArrayList;
import java.util.List;

public class Administrator extends User implements IAdministrator{

    public List<Requester> requesterList;
    //Default constructor with default authentication info
    public Administrator(int userId,String firstName, String lastName, String email, String password,  String role){
        super(userId,firstName, lastName, email, password, role);
        requesterList = new ArrayList<>();
    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

    //Requester control methods
    public void addRequester(){}
    public void deleteRequester(){}
    public void UpdateRequester(){}
    public void clearDatabase(){}
    public void resetDatabase(){}
    public void viewOrders(){}
    public void viewRequesters(){}


    //getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getUpdatedAt() {
        return updatedAt;
    }

    public List<Requester> getRequesterList() {
        return requesterList;
    }

    public void setRequesterList(List<Requester> requesterList) {
        this.requesterList = requesterList;
    }
}

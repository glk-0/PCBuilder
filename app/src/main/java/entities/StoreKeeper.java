package entities;


import java.util.ArrayList;
import java.util.List;

import inventory.Component;

public class StoreKeeper extends User implements IManageStock{

    public StoreKeeper(int userId,String firstName, String lastName, String email, String password, String role){
        super(userId,firstName,lastName,email,password,role);


    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void addComponent() {

    }

    @Override
    public void updateComponent() {

    }
    public void viewStock(){}

    @Override
    public void deleteComponent() {

    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }
}

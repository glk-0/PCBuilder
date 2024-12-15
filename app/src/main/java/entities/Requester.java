package entities;


import java.io.Serializable;

public class Requester extends User implements IManageRequests, Serializable {

    //Constructor with the first name and last name because optional

    public Requester(int userId, String firstName, String lastName, String email, String password, String role){
        super(userId,firstName, lastName,email,password,role);

    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }

    //Order Management methods
    public void createOrder(){}
    public void viewOrders(){};
    public void deleteOrder(){};

    //getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return

                "firstName= " + firstName + '\n' +
                        "lastName= " + lastName + '\n' +
                        "email= " + email + '\n' +
                "password= " + password + '\n'
                ;
    }
}

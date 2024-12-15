package entities;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public abstract class User {
    protected int userId;
    protected String email;
    protected String password;
    protected String role;
    protected String createdAt;
    protected String updatedAt;
    protected String firstName;
    protected String lastName;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //Constructor for all entities class
    public User(int userId, String firstName, String lastName, String email, String password, String role) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = getCurrentDateTime();
        this.updatedAt = getCurrentDateTime();
    }

    //formatting the date according to the DateTimeFormatter
    private String getCurrentDateTime() {
        return LocalDateTime.now().format(dtf);
    }
    public void updateTimestamp() {
        this.updatedAt = getCurrentDateTime();  // Set current time for updatedAt
    }
    public abstract void login();
    public abstract void logout();

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

package entities;


public class Assembler extends User implements IAssembler{

    public Assembler(int userId, String firstName, String lastName, String email, String password, String role){
        super(userId,firstName, lastName, email, password, role);
    }

    @Override
    public void login() {

    }

    @Override
    public void logout() {

    }
    //Will change return type to Request class once created
    public void viewPendingRequests(){};
    public void validateRequest(){};
    public void rejectRequest(){};
    public void approveRequest(){};


    //getters
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

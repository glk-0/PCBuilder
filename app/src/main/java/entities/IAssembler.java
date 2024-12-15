package entities;

public interface IAssembler extends IUsers {
    public void validateRequest();
    public void rejectRequest();
    public void approveRequest();
}

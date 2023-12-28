public interface IUserDAO {
    boolean loginUser(String username, String password);
    void insertNewUser();
}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserDAO implements IUserDAO{

	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;

	public UserDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public boolean loginUser(String username, String password) {
		String sql = "SELECT COUNT(*) FROM Users WHERE username = ? AND password = ?";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void insertNewUser() {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter username:");
		String username = scanner.nextLine();

		System.out.println("Enter password:");
		String password = scanner.nextLine(); 

		System.out.println("Enter email:");
		String email = scanner.nextLine();

		System.out.println("Enter address:");
		String address = scanner.nextLine();

		System.out.println("Enter mobile number:");
		String mobileNo = scanner.nextLine();

		System.out.println("Enter technology interested in (e.g., Java Full Stack, Python Full Stack):");
		String techInterested = scanner.nextLine();

		String sql = "INSERT INTO Users(username, password, email, address, mobile_no, technologies_interested) VALUES(?,?,?,?,?,?)";

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password); 
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, address);
			preparedStatement.setString(5, mobileNo);
			preparedStatement.setString(6, techInterested);

			preparedStatement.executeUpdate();
			System.out.println("User inserted successfully");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Insert new user operation completed.");
		}
	}
	
	public String getTechInterestOfUser(String username, String password) {
	    String sql = "SELECT technologies_interested FROM Users WHERE username = ? AND password = ?";
	    String techInterest = "";

	    try (Connection connection = getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                techInterest = resultSet.getString("technologies_interested");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return techInterest;
	}

	
	
}

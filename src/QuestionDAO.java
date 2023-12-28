import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestionDAO implements IQuestionDAO  {

    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
	Scanner scanner = new Scanner(System.in);

    public QuestionDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public void insertJavaQuestions() {
        String[] questions = {
        		"INSERT INTO questions (question_text, correct_option, technology) VALUES ('What is the size of a char in Java? A) 1 byte, B) 2 bytes, C) 3 bytes, D) 4 bytes', 'B', 'Java Full Stack')",
        		"INSERT INTO questions (question_text, correct_option, technology) VALUES ('Which keyword is used to create an object in Java? A) alloc, B) new, C) create, D) malloc', 'B', 'Java Full Stack')",
        		"INSERT INTO questions (question_text, correct_option, technology) VALUES ('In Java, which access specifier allows visibility only within the same package? A) private, B) public, C) protected, D) default (no specifier)', 'D', 'Java Full Stack')"
        };

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            for (String sql : questions) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Questions inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insertPythonQuestions() {
        String[] questions = {
            "INSERT INTO questions (question_text, correct_option, technology) VALUES ('What is used to define a block of code in Python language? A) Key, B) Brackets, C) Indentation, D) None of these', 'C', 'Python Full Stack')",
            "INSERT INTO questions (question_text, correct_option, technology) VALUES ('Which of the following is the correct extension of the Python file? A) .python, B) .pl, C) .py, D) .p', 'C', 'Python Full Stack')",
            "INSERT INTO questions (question_text, correct_option, technology) VALUES ('What does the `len()` function in Python used for? A) Returns the length of an object, B) Deletes an object, C) Compares two objects, D) None of these', 'A', 'Python Full Stack')"
        };

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            for (String sql : questions) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Python questions inserted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> viewQuestions(String technology) {
        String sql = "SELECT question_id, question_text, correct_option FROM questions WHERE TECHNOLOGY = ?";
        List<String> answers = new ArrayList<>();
        int index = 1;

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, technology);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("question_id");
                    String questionText = resultSet.getString("question_text");
                    System.out.println("Question ID: " + index);
                    System.out.println("Question: " + questionText);
                    System.out.println("");

                    System.out.println("Please enter your answer for " + index + ":");
                    String userAnswer = scanner.next().toUpperCase(); 
                    answers.add(userAnswer);
                    System.out.println("-----------------------------------");
                    index++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }
    
    public List<String> getCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<>();
        String sql = "SELECT correct_option FROM questions";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String correctOption = resultSet.getString("correct_option");
                correctAnswers.add(correctOption);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return correctAnswers;
    }


    
    
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PlatformHelper {

    static String jdbcURL = "jdbc:mysql://localhost:3306/user";
    static String jdbcUsername = "root";
    static String jdbcPassword = "12345";
    static UserDAO userDAO = new UserDAO(jdbcURL, jdbcUsername, jdbcPassword);
    static QuestionDAO questionDAO = new QuestionDAO(jdbcURL, jdbcUsername, jdbcPassword);
    private static final Scanner scanner = new Scanner(System.in);
    static String username;
    static String password;
    static void manageQuiz() {
        boolean keepRunning = true;

        while (keepRunning) {
            try {
                int choice = checkUser();

                switch (choice) {
                    case 1:
                        userDAO.insertNewUser();
                        keepRunning = false;  
                        break;
                    case 2:
                        System.out.println("Enter username:");
                        username = scanner.nextLine();
                        System.out.println("Enter password:");
                        password = scanner.nextLine();
                        boolean isAuthenticated = userDAO.loginUser(username, password);
                        
                        if (isAuthenticated) {
                            System.out.println("Login successful!");
                            keepRunning = false;  
                            startQuiz();
                        } else {
                            System.out.println("Login failed. Incorrect username or password.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    
    static void startQuiz() {
        try {
            int userChoice = takeQuiz();
            
            switch (userChoice) {
                case 1:
                	String technology = userDAO.getTechInterestOfUser(username,password);
                	System.out.println("Welcome to " + technology + " quiz");

                	List<String> answers = questionDAO.viewQuestions(technology);
                    double score = getScore(answers, questionDAO.getCorrectAnswers()) * 100;
                    System.out.printf("You scored: %.2f%%\n", score);
                    break;
                case 2:
                    System.out.println("Which question do you want to remove?");
                    int choice2 = scanner.nextInt();
                    scanner.nextLine(); // 
                    break;
                case 3:
                    //
                	questionDAO.insertPythonQuestions();
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } catch (Exception e) {
            System.out.println("Unknown exception: " + e.getMessage());
        }
        System.out.println("Quiz Finished, thanks for using Quiz System");
    }

    static int checkUser() {
        try {
            System.out.println("Are you a new user or returned user:");
            System.out.println("1. New User");
            System.out.println("2. Returned User");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            return choice;
        } catch (Exception e) {
            System.out.println("An error occurred while reading your choice: " + e.getMessage());
            return -1; 
        }
    }

    static int takeQuiz() {
        try {
            System.out.println("Which option do you want:");
            System.out.println("1. Take Quiz");
            System.out.println("2. Insert Questions");
            System.out.println("3. View Quiz Questions");
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            return choice;
        } catch (Exception e) {
            System.out.println("An error occurred while reading your quiz option: " + e.getMessage());
            return -1; 
        }
    }

    public static double getScore(List<String> userAnswers, List<String> correctAnswers) {
        int correctCount = 0;
        for (int i = 0; i < userAnswers.size(); i++) {
            if (userAnswers.get(i).equals(correctAnswers.get(i))) {
                correctCount++;
            }
        }
        return correctCount / (double) userAnswers.size();
    }
}

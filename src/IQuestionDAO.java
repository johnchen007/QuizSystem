import java.util.List;

public interface IQuestionDAO {
    void insertJavaQuestions();
    void insertPythonQuestions();
    List<String> viewQuestions(String technology);
    List<String> getCorrectAnswers();
}

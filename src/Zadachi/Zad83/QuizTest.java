package Zadachi.Zad83;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            quiz.addQuestion(sc.nextLine());
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}

class Quiz {

    private List<Question> questions;

    public Quiz() {
        questions = new LinkedList<>();
    }

    public void addQuestion(String questionData) {
        Question question;
        try {
            question = QuestionFactory.build(questionData);
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
            return;
        }
        questions.add(question);
    }

    public void printQuiz(PrintStream out) {
        PrintWriter printWriter = new PrintWriter(out);
        List<Question> sortedList = new ArrayList<>(questions);
        sortedList.sort(Comparator.comparingInt(Question::getPoints).reversed());
        sortedList.forEach(question -> {
            printWriter.printf("%s: %s Points %d Answer: %s\n",
                    question.getClassType(), question.getText(), question.getPoints(), question.getAnswer());
            printWriter.flush();
        });
    }

    public void answerQuiz(List<String> answers, PrintStream out) throws InvalidOperationException {
        if (answers.size() != questions.size())
            throw new InvalidOperationException("Answers and questions must be of same length!");

        PrintWriter printWriter = new PrintWriter(out);
        double totalPoints = 0;
        for (int i = 0; i < answers.size(); ++i) {
            totalPoints += questions.get(i).answerQuestion(answers.get(i));
            printWriter.printf("%d. %.2f\n", i+1, questions.get(i).answerQuestion(answers.get(i)));
            printWriter.flush();
        }
        printWriter.printf("Total points: %.2f\n", totalPoints);
        printWriter.flush();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}

class QuestionFactory {

    public static Question build(String questionData) throws InvalidOperationException {
        String[] parts = questionData.split(";");
        String type = parts[0];
        String text = parts[1];
        int points = Integer.parseInt(parts[2]);
        switch (type.toUpperCase()) {
            case "TF":
                boolean answer1 = Boolean.parseBoolean(parts[3]);
                return new TrueFalseQuestion(text, points, answer1);
            case "MC":
                char answer2 = parts[3].charAt(0);
                if (!"ABCDE".contains(String.valueOf(answer2)))
                    throw new InvalidOperationException(String.format("%c is not allowed option for this question", answer2));
                return new MultipleChoiceQuestion(text, points, answer2);
            default: throw new RuntimeException("build QuestionFactory error");
        }
    }

}

abstract class Question {

    private String text;
    private int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public String getText() {
        return text;
    }

    public String getClassType() {
        return this.getClass().toString().split("\\.")[2];
    }

    abstract public String getAnswer();

    abstract public double answerQuestion(String answerAttempt);

}

class TrueFalseQuestion extends Question {

    private boolean answer;

    public TrueFalseQuestion(String text, int points, boolean answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String getClassType() {
        return "True/False Question";
    }

    @Override
    public String getAnswer() {
        return String.valueOf(answer);
    }

    @Override
    public double answerQuestion(String answerAttempt) {
        if (answerAttempt.equals(String.valueOf(answer)))
            return getPoints();
        else
            return 0f;
    }

}

class MultipleChoiceQuestion extends Question {

    private char answer;

    public MultipleChoiceQuestion(String text, int points, char answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String getClassType() {
        return "Multiple Choice Question";
    }

    @Override
    public String getAnswer() {
        return String.valueOf(answer);
    }

    @Override
    public double answerQuestion(String answerAttempt) {
        if (answerAttempt.equals(String.valueOf(answer)))
            return getPoints();
        else
            return (-0.2) * getPoints();
    }

}

class InvalidOperationException extends Exception {

    public InvalidOperationException(String message) {
        super(message);
    }

}
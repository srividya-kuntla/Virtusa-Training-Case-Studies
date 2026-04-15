import java.util.*;

class Question {
    String question;
    String[] options;
    int correctAnswer;

    public Question(String question, String[] options, int correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public boolean askQuestion(Scanner sc) {
        System.out.println("\n" + question);
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        System.out.print("Enter your answer (1-4): ");
        int answer = sc.nextInt();

        return answer == correctAnswer;
    }
}

public class QuizSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Question> quiz = new ArrayList<>();

        // Admin adds questions
        quiz.add(new Question(
                "What is the capital of India?",
                new String[]{"Mumbai", "Delhi", "Chennai", "Kolkata"},
                2
        ));

        quiz.add(new Question(
                "Which language is used for Android development?",
                new String[]{"Python", "Java", "C++", "HTML"},
                2
        ));

        quiz.add(new Question(
                "What is 5 + 3?",
                new String[]{"5", "8", "10", "6"},
                2
        ));

        int score = 0;
        long startTime = System.currentTimeMillis();
        int timeLimit = 30; // seconds

        System.out.println("===== ONLINE QUIZ START =====");
        System.out.println("You have " + timeLimit + " seconds.\n");

        for (Question q : quiz) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = (currentTime - startTime) / 1000;

            if (elapsedTime > timeLimit) {
                System.out.println("\nTime's up!");
                break;
            }

            if (q.askQuestion(sc)) {
                score++;
            }
        }

        // Result
        System.out.println("\n===== RESULT =====");
        System.out.println("Total Questions: " + quiz.size());
        System.out.println("Correct Answers: " + score);
        System.out.println("Wrong Answers: " + (quiz.size() - score));

        double percentage = (score * 100.0) / quiz.size();
        System.out.println("Score: " + percentage + "%");

        // Performance Analysis
        if (percentage >= 80) {
            System.out.println("Performance: Excellent");
        } else if (percentage >= 50) {
            System.out.println("Performance: Good");
        } else {
            System.out.println("Performance: Needs Improvement");
        }

        sc.close();
    }
}
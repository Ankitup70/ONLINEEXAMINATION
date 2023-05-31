import java.util.Scanner;

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profile = "";
    }

    public String getUsername() {
        return username;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void updateProfile(String profile) {
        this.profile = profile;
        System.out.println("Profile updated successfully.");
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
        System.out.println("Password changed successfully.");
    }
}

class MCQExam {
    private String[] questions;
    private String[][] options;
    private int[] correctAnswers;
    private boolean[] userAnswers;
    private int timeLimit;

    public MCQExam(String[] questions, String[][] options, int[] correctAnswers, int timeLimit) {
        this.questions = questions;
        this.options = options;
        this.correctAnswers = correctAnswers;
        this.userAnswers = new boolean[questions.length];
        this.timeLimit = timeLimit;
    }

    public void startExam() {
        Scanner scanner = new Scanner(System.in);
        int remainingTime = timeLimit * 60;

        System.out.println("Welcome to the MCQ Exam!");
        System.out.println("You have " + timeLimit + " minutes to complete the exam.");
        System.out.println("Please select the correct option for each question.");

        while (remainingTime > 0) {
            for (int i = 0; i < questions.length; i++) {
                if (!userAnswers[i]) {
                    System.out.println();
                    System.out.println("Question " + (i + 1) + ": " + questions[i]);
                    for (int j = 0; j < options[i].length; j++) {
                        System.out.println((j + 1) + ". " + options[i][j]);
                    }
                    System.out.print("Enter your choice (1-" + options[i].length + "): ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    if (choice >= 1 && choice <= options[i].length) {
                        userAnswers[i] = (choice == correctAnswers[i]);
                        System.out.println("Answer recorded.");
                    } else {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            }

            remainingTime--;

            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nTime's up! Exam has been auto-submitted.");
        scanner.close();
    }

    public void showResults() {
        int correctCount = 0;
        int incorrectCount = 0;

        System.out.println("Exam Results:");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i]);
            System.out.println("Your answer: " + (userAnswers[i] ? "Correct" : "Incorrect"));
            System.out.println("Correct answer: " + options[i][correctAnswers[i] - 1]);
            System.out.println();

            if (userAnswers[i]) {
                correctCount++;
            } else {
                incorrectCount++;
            }
        }

        System.out.println("Total Correct Answers: " + correctCount);
        System.out.println("Total Incorrect Answers: " + incorrectCount);
    }
}

public class OnlineExamSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = null;
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.println("Welcome to the Online Exam System!");

            System.out.print("Enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            // Simulating user authentication
            user = new User("admin", "password");

            if (user.authenticate(username, password)) {
                loggedIn = true;
                System.out.println("Login successful!\n");
            } else {
                System.out.println("Invalid username or password. Please try again.\n");
            }
        }

        boolean quit = false;

        while (!quit) {
            System.out.println("Online Exam Menu:");
            System.out.println("1. Update Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Start MCQ Exam");
            System.out.println("4. Quit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter your profile information: ");
                    String profile = scanner.nextLine();
                    user.updateProfile(profile);
                    break;
                case 2:
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    user.changePassword(newPassword);
                    break;
                case 3:
                    String[] questions = {
                            "What is the capital of France?",
                            "Which planet is known as the Red Planet?",
                            "Who painted the Mona Lisa?"
                    };
                    String[][] options = {
                            {"Paris", "London", "Rome", "Berlin"},
                            {"Mars", "Venus", "Saturn", "Jupiter"},
                            {"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo"}
                    };
                    int[] correctAnswers = {1, 1, 1};
                    int timeLimit = 2; // 2 minutes

                    MCQExam exam = new MCQExam(questions, options, correctAnswers, timeLimit);
                    exam.startExam();
                    exam.showResults();
                    break;
                case 4:
                    quit = true;
                    System.out.println("Thank you for using the Online Exam System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }

        scanner.close();
    }
}

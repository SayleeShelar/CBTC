import java.util.Random;
import java.util.Scanner;

public class GuessGame {
    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 10;
    private static final int MAX_ROUNDS = 3;

    public static void main(String[] args) {
        Random random = new Random();
        int totalScore = 0;

        System.out.println("Number Guessing Game");
        System.out.println("Total Number of Rounds: 3");
        System.out.println("Attempts To Guess Number In Each Round: 10\n");

        try (Scanner scanner = new Scanner(System.in)) {
            for (int i = 1; i <= MAX_ROUNDS; i++) {
                int number = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
                int attempts = 0;

                System.out.printf("Round %d: Guess the number between %d and %d in %d attempts.\n", i, MIN_RANGE, MAX_RANGE, MAX_ATTEMPTS);
                while (attempts < MAX_ATTEMPTS) {
                    System.out.print("Enter your guess: ");

                    // Ensure input is read correctly
                    String input = scanner.nextLine();
                    try {
                        int guessNumber = Integer.parseInt(input.trim()); // Convert input to integer

                        if (guessNumber < MIN_RANGE || guessNumber > MAX_RANGE) {
                            System.out.printf("Your guess should be between %d and %d.\n", MIN_RANGE, MAX_RANGE);
                            continue;
                        }

                        attempts++;

                        if (guessNumber == number) {
                            int score = MAX_ATTEMPTS - attempts + 1;
                            totalScore += score;
                            System.out.printf("You Guessed Right. Attempts = %d. Round Score = %d\n", attempts, score);
                            break;
                        } else if (guessNumber < number) {
                            System.out.printf("The number is greater than %d. Attempts Left = %d.\n", guessNumber, MAX_ATTEMPTS - attempts);
                        } else {
                            System.out.printf("The number is less than %d. Attempts Left = %d\n", guessNumber, MAX_ATTEMPTS - attempts);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

                if (attempts == MAX_ATTEMPTS) {
                    System.out.printf("Round %d: You did not guess the number. The correct number was %d.\n\n", i, number);
                } else {
                    System.out.printf("End of Round %d\n\n", i);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        System.out.printf("Game Over. Total Score = %d\n", totalScore);
    }
}

package other;

import java.util.Random;

public class GuessIt {
    public static void main(String[] args) {
        int randomNum = new Random().nextInt(10) + 1;
        String guessedNumText = null;

        while (!"q".equals(guessedNumText)) {
            guessedNumText = System.console().readLine("Please guess a number between 1 and 10 inclusively: ");
            int guessedNum = Integer.parseInt(guessedNumText);

            if (guessedNum == randomNum) {
                System.out.printf("The random number was %d. You got it!%n", randomNum);
                return;
            } else {
                System.out.printf("You didn't get it!%n");
            }
        }
    }
}


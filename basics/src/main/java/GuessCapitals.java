import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GuessCapitals {

    public static void evaluateTheResult(int score, int numberOfQuestions) {

        int result = score * 100 / numberOfQuestions;
        System.out.print("Your score is " + score + "/" + numberOfQuestions + "!");
        if (result == 100) {
            System.out.println(" Brilliant!");
        } else if (result < 100 && result >= 70) {
            System.out.println(" Great!");
        } else if (result < 70 && result > 40) {
            System.out.println(" Not bad!");
        } else if (result <= 40 && result > 0) {
            System.out.println(" You can do better!");
        } else if (result == 0) {
            System.out.println(" You even didn't try!");
        }
    }

    public static void main(String[] args) {

        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Belarus", "Minsk");
        hashMap.put("Russia", "Moscow");
        hashMap.put("Ukraine", "Kiev");
        hashMap.put("Germany", "Berlin");
        hashMap.put("Spain", "Madrid");
        hashMap.put("Italy", "Rome");
        hashMap.put("France", "Paris");
        hashMap.put("China", "Beijing");
        hashMap.put("USA", "Washington");
        hashMap.put("Peru", "Lima");

        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            System.out.println("What is the capital of " + entry.getKey() + "?");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(entry.getValue())) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong! The capital of " + entry.getKey() + " is " + entry.getValue() + ".");
            }
        }
        evaluateTheResult(score, hashMap.size());
    }
}

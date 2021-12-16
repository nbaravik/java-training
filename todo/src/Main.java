import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to task app. Please, enter, how many tasks you want to add: ");
        int length = Integer.parseInt(scanner.nextLine());

        List<Task> tasks = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            System.out.printf("Let's create task #%d.\n", i + 1);
            Task newTask = new Task();
            System.out.println("Enter name:");
            newTask.setName(scanner.nextLine());
            System.out.println("Enter date:");
            newTask.setDate(scanner.nextLine());
            System.out.println("Enter minutes:");
            newTask.setTime(Integer.parseInt(scanner.nextLine()));
            tasks.add(newTask);
        }

        System.out.println("Your tasks :" + '\n');
        System.out.printf(Task.FORMAT, "name", "date", "minutes");
        for (Task curTask : tasks) {
            System.out.print(curTask.toString());
        }
    }
}
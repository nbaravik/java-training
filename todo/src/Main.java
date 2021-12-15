import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to task app. Please, enter, how many tasks you want to add: ");
        int length = Integer.parseInt(scanner.nextLine());

        Task[] tasks = new Task[length];

        for (int i = 0; i < length; i++) {
            System.out.printf("Let's create task #%d.\n", i + 1);
            tasks[i] = new Task();
            System.out.println("Enter name:");
            tasks[i].setName(scanner.nextLine());
            System.out.println("Enter date:");
            tasks[i].setDate(scanner.nextLine());
            System.out.println("Enter minutes:");
            tasks[i].setTime(Integer.parseInt(scanner.nextLine()));
        }

        System.out.println("Your tasks :" + '\n');
        System.out.printf("%-20s%-10s%-10s%n", "name", "date", "minutes");
        for (Task curTask : tasks) {
            System.out.print(curTask.toString());
        }
    }
}

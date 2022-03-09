import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static String enteredCommand(String command) {

        String string = command.toLowerCase().trim();
        switch (string) {
            case "list":
            case "list done":
            case "exit": {
                return string;
            }
        }
        String[] array = string.split(" ", 2);
        String substring = array[0];

        switch (substring) {
            case "new": {
                if (array.length == 1) {
                    System.out.print("You did not enter task description. ");
                    return "";
                } else {
                    return substring;
                }
            }
            case "done": {
                if (array.length == 1) {
                    System.out.print("You did not enter task ID. ");
                    return "";
                } else {
                    return substring;
                }
            }
        }
        return "";
    }

    public static boolean toDoMethod(List<Task> tasks, String nextLine) {

        String command = enteredCommand(nextLine);

        switch (command) {

            case "new": {
                String taskDescription = nextLine.substring(nextLine.indexOf(' ') + 1);
                Task newTask = new Task(tasks.size() + 1, taskDescription);
                tasks.add(newTask);
                System.out.println("task #" + newTask.getID() + " created");
                break;
            }
            case "done": {
                try {
                    int taskDoneId = Integer.parseInt(nextLine.substring(nextLine.indexOf(' ') + 1));
                    if (taskDoneId > 0 && taskDoneId <= tasks.size()) {
                        Task doneTask = tasks.get(taskDoneId - 1);
                        doneTask.setDone(true);
                        System.out.println("task " + doneTask.getID() + " done");
                    } else {
                        System.out.println("Task with ID=" + taskDoneId + " does not exist. Command cannot be executed!");
                    }
                } catch (NumberFormatException numberFormatException) {
                    System.out.println("Task ID must be a number. Command cannot be executed!");
                    break;
                }

                break;
            }
            case "list": {
                System.out.printf(Task.FORMAT_2_COLUMNS, "task", "name");
                for (Task curTask : tasks) {
                    if (!curTask.getDone()) {
                        System.out.print(curTask);
                    }
                }
                break;
            }
            case "list done": {
                System.out.printf(Task.FORMAT_2_COLUMNS, "task", "name");
                for (Task curTask : tasks) {
                    if (curTask.getDone()) {
                        System.out.print(curTask);
                    }
                }
                break;
            }
            case "exit": {
                System.out.println("Have a nice day!");
                return false;
            }
            default: {
                System.out.println("Command cannot be executed!");
            }
        }
        return true;
    }


    public static void main(String[] args) {

        System.out.println("Welcome to To do app. Supported commands: new <task description>, done <id of task>, list, list done, exit.");
        Scanner scanner = new Scanner(System.in);

        boolean continueFlag = true;
        List<Task> tasks = new ArrayList<>();

        while (continueFlag) {
            String nextLine = scanner.nextLine();
            continueFlag = toDoMethod(tasks, nextLine.trim());
        }
    }
}
package com.taskmanager;

import java.util.Scanner;

public class TaskManagerCLI {
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        boolean running = true;
        try (Scanner scanner = new Scanner(System.in)){
            if (args.length == 0) {
                while (running) {

                    System.out.println("Choose an option: 1. Add task, 2. List tasks, 3. Complete task, 4. Delete task, 5. Quit");

                    String options = scanner.nextLine().trim();
                    String arg;
                    switch (Integer.parseInt(options)) {
                        case 1 -> {
                            System.out.print("Enter task description: ");
                            arg = scanner.nextLine().trim();
                            add(arg);
                        }
                        case 2 -> list();
                        case 3 -> {
                            System.out.println("Enter the index");
                            arg = scanner.nextLine().trim();
                            complete(Integer.parseInt(arg));
                        }
                        case 4 -> {
                            arg = scanner.nextLine().trim();
                            delete(Integer.parseInt(arg));
                        }
                        case 5 -> {
                            System.out.println("Exiting... ");
                            running = false;
                        }
                        default -> System.out.println("Not an option");
                    }
                }
            }
            else {
                String command = args[0];
                switch (command) {
                    case "add" -> {
                        if (args.length > 1) {
                            String description = String.join(" ",java.util.Arrays.copyOfRange(args, 1, args.length));
                            add(description);
                            System.out.println("Task added: " + description);
                        }
                        else {
                                System.out.println("Command 'add' requires a task description.");
                        }
                    }
                    case "list", "-l" -> list();
                    case "complete" -> {
                        if (args.length > 1) {
                            int id = Integer.parseInt(args[1]);
                            complete(id);
                            System.out.println("Task " + Integer.parseInt(args[1]) + " marked as completed");
                        }
                        else {
                            System.out.println("Command 'complete' requires a task ID");
                        }
                    }
                    case "delete", "rm" -> {
                        if (args.length > 1) {
                            int id = Integer.parseInt(args[1]);
                            delete(id);
                            System.out.println("Task " + Integer.parseInt(args[1]) + " has been deleted.");
                        }
                        else {
                            System.out.println("Command 'delete' requires a task ID");
                        }
                    }
                    case "help", "-h" -> showAllUsage();
                    default -> {
                        System.out.println("task: '" + command + "' is not a task command.");
                        System.out.println("Try 'task help' or '-h' for help.");
                    }
                }
            }
        } catch (NullPointerException | NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add(String description) {
        taskManager.addTask(description);
    }

    public static void list() {
        taskManager.listTasks();
    }

    public static void complete(int id) {
        taskManager.completedTask(id);
    }

    public static void delete(int id) {
        taskManager.deleteTask(id);
    }

    public static void showAllUsage() {
        System.out.println("usage: task <command> [options]");
        System.out.println();
        System.out.println("All valid commands for 'task' are shown below: ");
        System.out.println("    help,-h          Shows how to use task");
        System.out.println("    add              Add description to the task");
        System.out.println("    list,-l          List all the task backed up(If any)");
        System.out.println("    delete,rm        Deletes task for the given ID");
    }
}
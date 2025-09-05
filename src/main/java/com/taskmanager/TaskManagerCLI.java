package com.taskmanager;

import java.io.*;
import java.util.Scanner;

public class TaskManagerCLI {
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        boolean running = true;
        try (Scanner scanner = new Scanner(System.in)) {
            if (args.length == 0) {
                while (running) {

                    System.out.print("Choose an option: 1. Add task, 2. List tasks, 3. Complete task, 4. Delete task, 5. Quit");

                    String options = scanner.nextLine().trim();
                    String arg;
                    switch (Integer.parseInt(options)) {
                        case 1 -> {
                            System.out.print("Enter task description: ");
                            arg = scanner.nextLine().trim();
                            add(arg);
                            System.out.println("Task added: " + arg);
                        }
                        case 2 -> list();
                        case 3 -> {
                            System.out.print("Enter the task ID to complete: ");
                            arg = scanner.nextLine().trim();
                            complete(Integer.parseInt(arg));
                        }
                        case 4 -> {
                            System.out.print("Enter the task ID to delete: ");
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
            } else {
                String command = args[0];
                switch (command) {
                    case "add" -> {
                        if (args.length > 1) {
                            String description = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
                            add(description);
                            System.out.println("Task added: " + description);
                        } else {
                            System.out.println("Command 'add' requires a task description.");
                        }
                    }
                    case "list", "-l" -> list();
                    case "complete" -> {
                        if (args.length > 1) {
                            int id = Integer.parseInt(args[1]);
                            complete(id);
                        } else {
                            System.out.println("Command 'complete' requires a task ID");
                        }
                    }
                    case "delete", "rm" -> {
                        if (args.length > 1) {
                            int id = Integer.parseInt(args[1]);
                            delete(id);
                        } else {
                            System.out.println("Command 'delete' requires a task ID");
                        }
                    }
                    case "help", "-h" -> {
                        System.out.println(Config.getAppName() + " v" + Config.getAppVersion());
                        showAllUsage();
                    }
                    default -> {
                        System.out.println("task: '" + command + "' is not a task command.");
                        System.out.println("Try 'task help' or '-h' for help.");
                    }
                }
            }
        } catch (NullPointerException | NumberFormatException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void add(String description) {
        taskManager.addTask(description);
    }

    public static void list() {
        taskManager.listTasks();
        System.out.println("\nTotal tasks: " + taskManager.getTaskCount() + "/" + Config.getMaxTasks());
    }


    public static void complete(int id) {
        if (taskManager.completeTask(id)) {
            System.out.println("Task " + id + " marked as completed");
        } else {
            System.out.println("Task " + id + " not found");
        }
    }

    public static void delete(int id) {
        if (taskManager.deleteTask(id)) {
            System.out.println("Task " + id + " has been deleted.");
        } else {
            System.out.println("Task " + id + " not found.");
        }
    }

    public static void showAllUsage() {
        try (InputStream inputStream = TaskManagerCLI.class.getResourceAsStream("/help.txt")) {
            if (inputStream == null) {
                System.out.println("Help file not found in resources");
                return;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Help file not found");
        }
    }
}
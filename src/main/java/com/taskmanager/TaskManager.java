package com.taskmanager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> tasks;
    private int nextId;
    private final String filePath = "data/tasks.txt";

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
        loadFromFile();
    }

    public void addTask(String description) {
        Task newTask = new Task(nextId, description);
        tasks.add(newTask);
        nextId++;
        saveToFile();
    }

    public void listTasks() {
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public boolean completedTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setCompleted(true);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteTask(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    public Task getTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public int findTaskIndex(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return tasks.indexOf(task);
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public void saveToFile() {
        File fileDir = new File(filePath.substring(0,filePath.indexOf("/")));
        if (!fileDir.exists()) {
            if (fileDir.mkdirs()) {
                System.out.println("Directories created: " + fileDir.getAbsolutePath());
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                String line = task.getId() + "|\"" +
                              task.getDescription() + "\"|" +
                              task.getCompleted() + "|" +
                              task.getCreatedAt();
                writer.write(line);
                writer.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error Saving Task: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {
            String line;
            int maxId = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()){
                    parseAndAddTask(line);
                }
                for (Task task : tasks) {
                    if (task.getId() > maxId) maxId = task.getId();
                }
                nextId = maxId + 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error Loading Task: " + e.getMessage());
        }
    }

    public void parseAndAddTask(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length == 4) {

                int id = Integer.parseInt(parts[0]);

                String description = parts[1];
                if (description.startsWith("\"") && description.endsWith("\"")) {
                    description = description.substring(1, description.length() - 1);
                }

                boolean completed = Boolean.parseBoolean(parts[2]);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime storedTime = LocalDateTime.parse(parts[3], formatter);

                Task task = new Task(id, description);

                task.setCompleted(completed);
                task.setCreatedAt(storedTime);

                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("Skipping invalid line: " + line);
        }
    }
}
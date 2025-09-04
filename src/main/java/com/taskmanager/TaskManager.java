package com.taskmanager;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskManager {
    private final ArrayList<Task> tasks;
    private int nextId;
    private final String filePath = Config.getDataFilePath();

    public TaskManager() {
        this.tasks = new ArrayList<>();
        this.nextId = 1;
        loadFromFile();
    }

    public void addTask(String description) {
        if (tasks.size() >= Integer.parseInt(Config.getMaxTasks())) {
            System.out.println("Cannot add task: Maximum limit of " + Config.getMaxTasks() + " tasks reached!");
            return;
        }

        Task newTask = new Task(nextId, description);
        tasks.add(newTask);
        nextId++;
        saveToFile();
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found. Add some tasks first!");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public boolean completeTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setComplete(true);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean deleteTask(int id) {
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
    }

    public int getTaskCount() {
        return tasks.size();
    }

    public void saveToFile() {
        if (Boolean.parseBoolean(Config.getDebugMode())) {
            System.out.println("[DEBUG] Saving " + tasks.size() + " tasks to: " + filePath);
        }

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
                              task.isCompleted() + "|" +
                              task.getCreatedAt();
                writer.write(line);
                writer.newLine();
            }
            if (Boolean.parseBoolean(Config.getDebugMode())) {
                System.out.println("[DEBUG] Save completed successfully");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not Found!: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error Saving Task: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        if (Boolean.parseBoolean(Config.getDebugMode())) {
            System.out.println("[DEBUG] Loading tasks from: " + filePath);
        }
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader((new FileReader(filePath)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()){
                    parseAndAddTask(line);
                }
            }
            updateNextId();
            if (Boolean.parseBoolean(Config.getDebugMode())) {
                System.out.println("[DEBUG] Loaded " + tasks.size() + " tasks, nextId set to: " + nextId);
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

                task.setComplete(completed);
                task.setCreatedAt(storedTime);

                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("Skipping invalid line: " + line);
        }
    }

    private void updateNextId() {
        int maxId = 0;
        for (Task task : tasks) {
            if (task.getId() > maxId) {
                maxId = task.getId();
            }
        }
        nextId = maxId + 1;
    }
}
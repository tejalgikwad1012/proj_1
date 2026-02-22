package tejal;

import java.util.*;
import java.io.*;

// Task class
class Task implements Comparable<Task>, Serializable {
    private String title;
    private int priority;
    private String deadline;

    public Task(String title, int priority, String deadline) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public int getPriority() {
        return priority;
    }

    public String getDeadline() {
        return deadline;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // Higher priority first
    }

    @Override
    public String toString() {
        return "Title: " + title + " | Priority: " + priority + " | Deadline: " + deadline;
    }
}

public class kkk

{
    private static PriorityQueue<Task> taskQueue = new PriorityQueue<>();
    private static final String FILE_NAME = "tasks.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadTasks();
        startReminder();

        while (true) {
            System.out.println("\n===== SMART TASK SCHEDULER =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Delete Highest Priority Task");
            System.out.println("4. Save Tasks");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addTask(sc);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    deleteTask();
                    break;
                case 4:
                    saveTasks();
                    break;
                case 5:
                    saveTasks();
                    System.out.println("Exiting... Tasks saved.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void addTask(Scanner sc) {
        System.out.print("Enter Task Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Priority (1-High, 5-Low): ");
        int priority = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Deadline (DD-MM-YYYY): ");
        String deadline = sc.nextLine();

        Task task = new Task(title, priority, deadline);
        taskQueue.add(task);
        System.out.println("Task Added Successfully!");
    }

    private static void viewTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.println("\n--- Task List (By Priority) ---");
        PriorityQueue<Task> temp = new PriorityQueue<>(taskQueue);
        while (!temp.isEmpty()) {
            System.out.println(temp.poll());
        }
    }

    private static void deleteTask() {
        if (taskQueue.isEmpty()) {
            System.out.println("No tasks to delete.");
        } else {
            Task removed = taskQueue.poll();
            System.out.println("Removed Task: " + removed.getTitle());
        }
    }

    private static void saveTasks() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(new ArrayList<>(taskQueue));
            System.out.println("Tasks saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    private static void loadTasks() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ArrayList<Task> list = (ArrayList<Task>) ois.readObject();
            taskQueue.addAll(list);
            System.out.println("Tasks loaded from file.");
        } catch (Exception e) {
            System.out.println("No previous tasks found.");
        }
    }

    private static void startReminder() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (!taskQueue.isEmpty()) {
                    Task top = taskQueue.peek();
                    System.out.println("\n🔔 Reminder: Highest Priority Task -> " + top.getTitle());
                }
            }
        }, 0, 30000); // Reminder every 30 seconds
    }
}
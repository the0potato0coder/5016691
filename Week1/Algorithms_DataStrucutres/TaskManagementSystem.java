// Different Types of Linked Lists

// Singly Linked List:

// Structure: Each node contains data and a reference (or link) to the next node in the sequence.
// Advantages: Simple to implement and efficient for inserting and deleting nodes.
// Disadvantages: Only allows traversal in one direction.

// Doubly Linked List:

// Structure: Each node contains data, a reference to the next node, and a reference to the previous node.
// Advantages: Allows traversal in both directions (forward and backward).
// Disadvantages: Requires more memory due to the extra reference to the previous node and is slightly more complex to implement.

import java.util.Scanner;

class Task {
    private String taskId;
    private String taskName;
    private String status;

    public Task(String taskId, String taskName, String status) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.status = status;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

class Node {
    Task task;
    Node next;

    public Node(Task task) {
        this.task = task;
        this.next = null;
    }
}

class SinglyLinkedList {
    private Node head;

    public SinglyLinkedList() {
        this.head = null;
    }

    // Add a new task
    public void addTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    // Search for a task by ID
    public Task searchTask(String taskId) {
        Node current = head;
        while (current != null) {
            if (current.task.getTaskId().equals(taskId)) {
                return current.task;
            }
            current = current.next;
        }
        return null;
    }

    // Traverse and display all tasks
    public void traverseTasks() {
        Node current = head;
        while (current != null) {
            System.out.println(current.task);
            current = current.next;
        }
    }

    // Delete a task by ID
    public void deleteTask(String taskId) {
        if (head == null) return;

        if (head.task.getTaskId().equals(taskId)) {
            head = head.next;
            return;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.task.getTaskId().equals(taskId)) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
        }
        System.out.println("Task not found.");
    }
}

public class TaskManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SinglyLinkedList taskList = new SinglyLinkedList();

        while (true) {
            System.out.println("1. Add Task");
            System.out.println("2. Search Task");
            System.out.println("3. Traverse Tasks");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter task name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter status: ");
                    String status = scanner.nextLine();
                    Task task = new Task(id, name, status);
                    taskList.addTask(task);
                    break;
                case 2:
                    System.out.print("Enter task ID to search: ");
                    String searchId = scanner.nextLine();
                    Task foundTask = taskList.searchTask(searchId);
                    if (foundTask != null) {
                        System.out.println("Task found: " + foundTask);
                    } else {
                        System.out.println("Task not found.");
                    }
                    break;
                case 3:
                    taskList.traverseTasks();
                    break;
                case 4:
                    System.out.print("Enter task ID to delete: ");
                    String deleteId = scanner.nextLine();
                    taskList.deleteTask(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



// Time Complexity

// Add Operation:
// Best Case: O(1) - Adding to an empty list.
// Worst Case: O(n) - Adding to the end of the list.

// Search Operation:
// Best Case: O(1) - The task is the first node.
// Worst Case: O(n) - The task is not in the list, or it is the last node.

// Traverse Operation:
// Always O(n) - Needs to visit every node in the list.

// Delete Operation:
// Best Case: O(1) - The task is the first node.
// Worst Case: O(n) - The task is not in the list, or it is the last node.





// Advantages of Linked Lists Over Arrays

// Dynamic Size: Linked lists can grow or shrink in size dynamically, unlike arrays, which have a fixed size.

// Efficient Insertions/Deletions: Insertions and deletions can be more efficient with linked lists, especially when dealing with a large number of elements, as there is no need to shift elements as in arrays.

// Memory Utilization: Linked lists use memory more efficiently for dynamic data as they allocate memory as needed, unlike arrays which allocate a fixed block of memory.
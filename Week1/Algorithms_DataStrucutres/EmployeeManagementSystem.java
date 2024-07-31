// Representation of  Arrays in memory:

// Contiguous Memory Allocation: Arrays are stored in contiguous memory locations. This means that each element of the array is placed next to its neighboring element in memory. This allows for efficient indexing.
// Indexing: Arrays provide O(1) time complexity for accessing elements by index, as the address of any element can be calculated using its index and the base address of the array.
// Fixed Size: The size of an array is determined at the time of its creation and cannot be changed. This allows for efficient memory allocation but can be limiting if the number of elements is unknown or dynamic.


// Advantages of Arrays:

// Fast Access: O(1) time complexity for accessing elements by index.
// Predictable Memory Usage: Since the size is fixed, the memory usage is predictable.
// Cache-Friendly: Contiguous memory allocation makes arrays more cache-friendly, leading to faster access times.

import java.util.Scanner;

class Employee {
    private int employeeId;
    private String name;
    private String position;
    private double salary;

    public Employee(int employeeId, String name, String position, double salary) {
        this.employeeId = employeeId;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }
}

public class EmployeeManagementSystem {
    private Employee[] employees;
    private int size;

    public EmployeeManagementSystem(int capacity) {
        employees = new Employee[capacity];
        size = 0;
    }

    public void addEmployee(Employee employee) {
        if (size < employees.length) {
            employees[size] = employee;
            size++;
        } else {
            System.out.println("Employee array is full. Cannot add more employees.");
        }
    }

    public Employee searchEmployee(int employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                return employees[i];
            }
        }
        return null;
    }

    public void traverseEmployees() {
        for (int i = 0; i < size; i++) {
            System.out.println(employees[i]);
        }
    }

    public void deleteEmployee(int employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId() == employeeId) {
                employees[i] = employees[size - 1];
                employees[size - 1] = null;
                size--;
                return;
            }
        }
        System.out.println("Employee not found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem ems = new EmployeeManagementSystem(10);

        while (true) {
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Traverse Employees");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter position: ");
                    String position = scanner.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    Employee employee = new Employee(id, name, position, salary);
                    ems.addEmployee(employee);
                    break;
                case 2:
                    System.out.print("Enter employee ID to search: ");
                    int searchId = scanner.nextInt();
                    Employee foundEmployee = ems.searchEmployee(searchId);
                    if (foundEmployee != null) {
                        System.out.println("Employee found: " + foundEmployee);
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;
                case 3:
                    ems.traverseEmployees();
                    break;
                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int deleteId = scanner.nextInt();
                    ems.deleteEmployee(deleteId);
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
// Best Case: O(1) - Adding to the end of the array.
// Worst Case: O(1) - Always adding to the end of the array.

// Search Operation:
// Best Case: O(1) - The employee is the first element.
// Worst Case: O(n) - The employee is not in the array, or is the last element.

// Traverse Operation:
// Always O(n) - Needs to visit every element in the array.

// Delete Operation:
// Best Case: O(1) - The employee is the last element.
// Worst Case: O(n) - The employee is the first element, and we have to shift all subsequent elements.

// Limitations of Arrays

// Fixed Size: Arrays cannot change size once initialized. This can lead to inefficient memory usage if the array is underutilized, or inability to add new elements if the array is full.
// Insertion and Deletion: Inserting or deleting elements (other than at the end) requires shifting elements, which can be inefficient (O(n)).
// Wasted Space: If the maximum capacity is not utilized, the array will have unused space.
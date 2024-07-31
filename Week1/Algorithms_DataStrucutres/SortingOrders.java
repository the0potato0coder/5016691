// Sorting Algorithms :

// Bubble Sort
// A simple comparison-based algorithm where each pair of adjacent elements is compared and swapped if they are in the wrong order. This process is repeated until the list is sorted.

// Insertion Sort
// Builds the sorted array one item at a time by repeatedly taking the next element from the unsorted portion and inserting it into its correct position in the sorted portion.

// Quick Sort
// A divide-and-conquer algorithm that selects a pivot element and partitions the array into two sub-arrays (elements less than the pivot and elements greater than the pivot), then recursively sorts the sub-arrays.

// Merge Sort
// A divide-and-conquer algorithm that divides the array into two halves, recursively sorts them, and then merges the sorted halves.

import java.util.Scanner;

public class SortingOrders {

    static class Order {
        private String orderId;
        private String customerName;
        private double totalPrice;

        public Order(String orderId, String customerName, double totalPrice) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.totalPrice = totalPrice;
        }

        public String getOrderId() { return orderId; }
        public String getCustomerName() { return customerName; }
        public double getTotalPrice() { return totalPrice; }

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", customerName='" + customerName + '\'' +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

    // Bubble Sort implementation
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (orders[j].getTotalPrice() > orders[j + 1].getTotalPrice()) {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    private static void swap(Order[] orders, int i, int j) {
        Order temp = orders[i];
        orders[i] = orders[j];
        orders[j] = temp;
    }

    // Quick Sort implementation
    public static void quickSort(Order[] orders, int low, int high) {
        if (low < high) {
            int pi = partition(orders, low, high);
            quickSort(orders, low, pi - 1);
            quickSort(orders, pi + 1, high);
        }
    }

    private static int partition(Order[] orders, int low, int high) {
        double pivot = orders[high].getTotalPrice();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (orders[j].getTotalPrice() <= pivot) {
                i++;
                swap(orders, i, j);
            }
        }
        swap(orders, i + 1, high);
        return i + 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of orders: ");
        int numOrders = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Order[] orders = new Order[numOrders];

        for (int i = 0; i < numOrders; i++) {
            System.out.println("Enter details for order " + (i + 1) + ":");
            System.out.print("Order ID: ");
            String orderId = scanner.nextLine();
            System.out.print("Customer Name: ");
            String customerName = scanner.nextLine();
            System.out.print("Total Price: ");
            double totalPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline

            orders[i] = new Order(orderId, customerName, totalPrice);
        }

        System.out.println("\nOriginal Orders:");
        for (Order order : orders) {
            System.out.println(order);
        }

        // Bubble Sort
        Order[] bubbleSortedOrders = orders.clone();
        bubbleSort(bubbleSortedOrders);
        System.out.println("\nOrders sorted by Bubble Sort:");
        for (Order order : bubbleSortedOrders) {
            System.out.println(order);
        }

        // Quick Sort
        Order[] quickSortedOrders = orders.clone();
        quickSort(quickSortedOrders, 0, quickSortedOrders.length - 1);
        System.out.println("\nOrders sorted by Quick Sort:");
        for (Order order : quickSortedOrders) {
            System.out.println(order);
        }

        scanner.close();
    }
}

// Analysis:
// Bubble Sort:

// Time Complexity:

// Best Case: O(n) (when the array is already sorted)
// Average Case: O(n^2)
// Worst Case: O(n^2)


// Quick Sort:

// Time Complexity:

// Best Case: O(n log n)
// Average Case: O(n log n)
// Worst Case: O(n^2) (rare, with poor pivot selection)

// While Bubble Sort is simple and easy to implement, it is not suitable for large datasets due to its quadratic time complexity. Quick Sort, on the other hand, offers much better performance for sorting large datasets, making it the preferred choice for an e-commerce platform where sorting customer orders efficiently is crucial for prioritizing high-value orders.
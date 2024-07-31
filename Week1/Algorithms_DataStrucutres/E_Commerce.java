// Big O Notation

// Definition: Big O notation is a mathematical representation used to describe the upper bound of an algorithm's time or space complexity as a function of the input size n. It characterizes the worst-case scenario, giving an upper limit on the time or space an algorithm will require, regardless of specific input.

// Purpose: Helps to understand the efficiency and scalability of algorithms by abstracting away constant factors and lower-order terms. It provides a high-level understanding of how an algorithm's performance grows with the input size.



// Best, Average, and Worst-Case Scenarios for Search Operations

// Best-Case Scenario:
// Definition: The condition where the algorithm performs the minimum number of operations.

// Average-Case Scenario:
// Definition: The expected number of operations performed by the algorithm, averaged over all possible inputs.

// Worst-Case Scenario:
// Definition: The condition where the algorithm performs the maximum number of operations.


import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Product {
    private int productId;
    private String productName;
    private String category;

    public Product(int productId, String productName, String category) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
    }

    public int getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

public class E_Commerce {
    // Linear search implementation
    public static Product linearSearch(Product[] products, String productName) {
        for (Product product : products) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    // Binary search implementation
    public static Product binarySearch(Product[] products, String productName) {
        // Sort the array first
        Arrays.sort(products, Comparator.comparing(Product::getProductName));

        int left = 0;
        int right = products.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = products[mid].getProductName().compareToIgnoreCase(productName);

            if (comparison == 0) {
                return products[mid];
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of products: ");
        int n = scanner.nextInt();
        scanner.nextLine(); 
        Product[] products = new Product[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter product ID: ");
            int productId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();
            System.out.print("Enter product category: ");
            String category = scanner.nextLine();
            products[i] = new Product(productId, productName, category);
        }
        System.out.print("Enter the product name to search: ");
        String searchProductName = scanner.nextLine();

        // Linear Search
        Product result = linearSearch(products, searchProductName);
        System.out.println("Linear Search Result: " + (result != null ? result : "Product not found"));

        // Binary Search
        result = binarySearch(products, searchProductName);
        System.out.println("Binary Search Result: " + (result != null ? result : "Product not found"));

        scanner.close();
    }
}



// Linear Search
// Best Case: O(1) - The target element is the first element in the array.
// Average Case: O(n/2) = O(n) - The target element is somewhere in the middle of the array.
// Worst Case: O(n) - The target element is the last element or not present in the array.

// Binary Search
// Best Case: O(1) - The target element is the middle element of the array.
// Average Case: O(log n) - The search space is halved with each step, resulting in logarithmic time.
// Worst Case: O(log n) - The target element is at the last position checked or not present in the array.

// Suitability:
// For an e-commerce platform with potentially large datasets, binary search is more suitable due to its logarithmic time complexity, which provides faster search results compared to linear search. Although binary search requires the dataset to be sorted, the performance benefits for search operations outweigh the overhead of maintaining the sorted order, making it the preferred choice for enhancing user experience on the platform.
// Importance of Data Structures and Algorithms

// Data structures and algorithms are crucial in handling large inventories because:

// Efficiency: Efficient data structures and algorithms ensure quick data retrieval, updates, and storage, which is vital for real-time inventory management.
// Scalability: As the inventory grows, the system must scale without significant performance degradation. Proper data structures help maintain performance.
// Memory Management: Efficient use of memory ensures that the system can handle large inventories without excessive memory usage, preventing slowdowns or crashes.



// Suitable Data Structures

// ArrayList: Suitable for maintaining a dynamic list of products where frequent access and updates are required. It provides O(1) time complexity for access but O(n) for adding or removing elements in the worst case.
// HashMap: Ideal for fast access, updates, and deletions using a product identifier as the key. It generally provides O(1) time complexity for these operations, making it highly efficient for large inventories.

import java.util.HashMap;
import java.util.Scanner;

class Product {
    private String productId;
    private String productName;
    private int quantity;
    private double price;

    public Product(String productId, String productName, int quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }


    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}


public class InventoryManagementSystem {
    private HashMap<String, Product> inventory;

    public InventoryManagementSystem() {
        inventory = new HashMap<>();
    }

    
    public void addProduct(Product product) {
        inventory.put(product.getProductId(), product);
    }

   
    public void updateProduct(String productId, Product updatedProduct) {
        if (inventory.containsKey(productId)) {
            inventory.put(productId, updatedProduct);
        } else {
            System.out.println("Product not found");
        }
    }

   
    public void deleteProduct(String productId) {
        if (inventory.containsKey(productId)) {
            inventory.remove(productId);
        } else {
            System.out.println("Product not found");
        }
    }

    
    public Product getProduct(String productId) {
        return inventory.get(productId);
    }

    public static void main(String[] args) {
        InventoryManagementSystem ims = new InventoryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Inventory Management System");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Get Product");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    String addId = scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String addName = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int addQuantity = scanner.nextInt();
                    System.out.print("Enter price: ");
                    double addPrice = scanner.nextDouble();
                    Product newProduct = new Product(addId, addName, addQuantity, addPrice);
                    ims.addProduct(newProduct);
                    System.out.println("Product added.");
                    break;
                case 2:
                    System.out.print("Enter product ID to update: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Enter new product name: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter new quantity: ");
                    int updateQuantity = scanner.nextInt();
                    System.out.print("Enter new price: ");
                    double updatePrice = scanner.nextDouble();
                    Product updatedProduct = new Product(updateId, updateName, updateQuantity, updatePrice);
                    ims.updateProduct(updateId, updatedProduct);
                    System.out.println("Product updated.");
                    break;
                case 3:
                    System.out.print("Enter product ID to delete: ");
                    String deleteId = scanner.nextLine();
                    ims.deleteProduct(deleteId);
                    System.out.println("Product deleted.");
                    break;
                case 4:
                    System.out.print("Enter product ID to retrieve: ");
                    String getId = scanner.nextLine();
                    Product product = ims.getProduct(getId);
                    if (product != null) {
                        System.out.println(product);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



// Time Complexity
// Add Product: O(1) on average, O(n) in the worst case.
// Update Product: O(1) on average.
// Delete Product: O(1) on average.
// Get Product: O(1) on average.

// Optimization Considerations
// Load Factor: Ensuring the load factor of the HashMap remains low (typically around 0.75) can help maintain O(1) time complexity for operations by reducing the likelihood of collisions.
// Resizing: HashMap automatically resizes when it reaches a certain capacity, which helps maintain average O(1) time complexity for operations. However, resizing itself is an O(n) operation but happens infrequently.
// Hash Function: Using a good hash function that uniformly distributes keys helps minimize collisions and maintain efficient time complexity.
// Explaining Search Algorithms

// Linear Search
// Explanation: Linear search algorithm scans each element of the list one by one until the target element is found or the list ends.

// Binary Search
// Explanation: Binary search algorithm finds the target element by repeatedly dividing the search interval in half. It requires the list to be sorted.

import java.util.Arrays;
import java.util.Scanner;

class Book {
    private int bookId;
    private String title;
    private String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

public class LibraryManagementSystem {

    // Linear search to find books by title
    public static Book linearSearchByTitle(Book[] books, String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    // Binary search to find books by title (assuming the list is sorted)
    public static Book binarySearchByTitle(Book[] books, String title) {
        int left = 0;
        int right = books.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int compare = books[mid].getTitle().compareToIgnoreCase(title);
            if (compare == 0) {
                return books[mid];
            } else if (compare < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Book[] books = new Book[0];

        while (true) {
            System.out.println("1. Add Book");
            System.out.println("2. Linear Search by Title");
            System.out.println("3. Binary Search by Title");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    Book newBook = new Book(bookId, title, author);
                    books = Arrays.copyOf(books, books.length + 1);
                    books[books.length - 1] = newBook;
                    Arrays.sort(books, (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle()));
                    break;
                case 2:
                    System.out.print("Enter book title for linear search: ");
                    String titleLinear = scanner.nextLine();
                    Book foundBookLinearTitle = linearSearchByTitle(books, titleLinear);
                    if (foundBookLinearTitle != null) {
                        System.out.println("Book found: " + foundBookLinearTitle);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter book title for binary search: ");
                    String titleBinary = scanner.nextLine();
                    Book foundBookBinaryTitle = binarySearchByTitle(books, titleBinary);
                    if (foundBookBinaryTitle != null) {
                        System.out.println("Book found: " + foundBookBinaryTitle);
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

// Time Complexity Comparison
// Linear Search:

// Best Case: O(1) - The target is the first element.
// Average Case: O(n/2) which simplifies to O(n) - The target is somewhere in the middle.
// Worst Case: O(n) - The target is the last element or not in the list.
// Binary Search:

// Best Case: O(1) - The target is the middle element.
// Average Case: O(log n) - Each step halves the search interval.
// Worst Case: O(log n) - The target is at either end or not in the list.



// When to use each algorithm based on the data set size and order

// The linear search is simpler but less efficient for large datasets, while the binary search is more efficient but requires the dataset to be sorted
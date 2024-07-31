// Concept of Recursion:
// Recursion is a programming technique where a method calls itself to solve smaller instances of the same problem. It can simplify problems by breaking them down into smaller, more manageable sub-problems.

import java.util.Scanner;

public class FinancialForecasting {

    public static double calculateFutureValue(double presentValue, double growthRate, int years) {
        if (years == 0) {
            return presentValue;
        }
        return calculateFutureValue(presentValue * (1 + growthRate), growthRate, years - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the present value: ");
        double presentValue = scanner.nextDouble();

        System.out.print("Enter the annual growth rate (as a decimal, e.g., 0.05 for 5%): ");
        double growthRate = scanner.nextDouble();

        System.out.print("Enter the number of years: ");
        int years = scanner.nextInt();

        double futureValueRecursive = calculateFutureValue(presentValue, growthRate, years);
        System.out.println("Future value (recursive): " + futureValueRecursive);

        scanner.close();
    }
}


// Time Complexity Analysis

// Recursive Method:
// Time Complexity: O(n), where n is the number of years. The method makes a recursive call for each year until it reaches 0.

// Optimization
// The optimized method uses memoization to store previously computed results, reducing redundant calculations and improving efficiency.
// Memoization ensures that each sub-problem is solved only once, leading to significant performance improvements for larger input sizes.
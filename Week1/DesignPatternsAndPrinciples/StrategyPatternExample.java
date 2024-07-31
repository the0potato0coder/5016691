package Week1.DesignPatternsAndPrinciples;

import java.util.Scanner;

public class StrategyPatternExample {

    interface PaymentStrategy {
        void pay(double amount);
    }

    static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;
        private String cardHolderName;
        private String cvv;
        private String expiryDate;

        public CreditCardPayment(String cardNumber, String cardHolderName, String cvv, String expiryDate) {
            this.cardNumber = cardNumber;
            this.cardHolderName = cardHolderName;
            this.cvv = cvv;
            this.expiryDate = expiryDate;
        }

        @Override
        public void pay(double amount) {
            System.out.println(amount + " paid using Credit Card.");
        }
    }

    static class PayPalPayment implements PaymentStrategy {
        private String email;
        private String password;

        public PayPalPayment(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        public void pay(double amount) {
            System.out.println(amount + " paid using PayPal.");
        }
    }

    // Implement Context Class
    static class PaymentContext {
        private PaymentStrategy paymentStrategy;

        public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
            this.paymentStrategy = paymentStrategy;
        }

        public void pay(double amount) {
            paymentStrategy.pay(amount);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PaymentContext context = new PaymentContext();

        System.out.println("Select Payment Method: 1 for Credit Card, 2 for PayPal");
        int choice = scanner.nextInt();
        if (choice == 1) {
            scanner.nextLine(); 
            System.out.print("Enter Credit Card Number: ");
            String cardNumber = scanner.nextLine();
            System.out.print("Enter Card Holder Name: ");
            String cardHolderName = scanner.nextLine();
            System.out.print("Enter CVV: ");
            String cvv = scanner.nextLine();
            System.out.print("Enter Expiry Date (MM/YY): ");
            String expiryDate = scanner.nextLine();
            
            PaymentStrategy creditCardPayment = new CreditCardPayment(cardNumber, cardHolderName, cvv, expiryDate);
            context.setPaymentStrategy(creditCardPayment);
        } else if (choice == 2) {
            scanner.nextLine(); 
            System.out.print("Enter PayPal Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter PayPal Password: ");
            String password = scanner.nextLine();
            
            PaymentStrategy payPalPayment = new PayPalPayment(email, password);
            context.setPaymentStrategy(payPalPayment);
        } else {
            System.out.println("Invalid choice.");
            scanner.close();
            return;
        }

        System.out.print("Enter amount to pay: ");
        double amount = scanner.nextDouble();
        context.pay(amount);

        scanner.close();
    }
}

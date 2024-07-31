package Week1.DesignPatternsAndPrinciples;

import java.util.Scanner;

public class AdapterPatternExample {

    interface PaymentProcessor {
        void processPayment(double amount);
    }
    static class Paytm {
        public void payAmount(double amount) {
            System.out.println("Processing payment of ₹" + amount + " through Paytm.");
        }
    }
    static class PhonePe {
        public void makePayment(double amount) {
            System.out.println("Processing payment of ₹" + amount + " through PhonePe.");
        }
    }
    static class Razorpay {
        public void doPayment(double amount) {
            System.out.println("Processing payment of ₹" + amount + " through Razorpay.");
        }
    }

    static class PaytmAdapter implements PaymentProcessor {
        private Paytm paytm;

        public PaytmAdapter(Paytm paytm) {
            this.paytm = paytm;
        }

        @Override
        public void processPayment(double amount) {
            paytm.payAmount(amount);
        }
    }

    static class PhonePeAdapter implements PaymentProcessor {
        private PhonePe phonePe;

        public PhonePeAdapter(PhonePe phonePe) {
            this.phonePe = phonePe;
        }

        @Override
        public void processPayment(double amount) {
            phonePe.makePayment(amount);
        }
    }

    static class RazorpayAdapter implements PaymentProcessor {
        private Razorpay razorpay;

        public RazorpayAdapter(Razorpay razorpay) {
            this.razorpay = razorpay;
        }

        @Override
        public void processPayment(double amount) {
            razorpay.doPayment(amount);
        }
    }
    
    public static class AdapterPatternTest {
        @SuppressWarnings("resource")
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the amount to be paid:");
            double amount = scanner.nextDouble();

            System.out.println("Choose the payment gateway: 1 for Paytm, 2 for PhonePe, 3 for Razorpay");
            int choice = scanner.nextInt();

            PaymentProcessor paymentProcessor;

            switch (choice) {
                case 1:
                    paymentProcessor = new PaytmAdapter(new Paytm());
                    break;
                case 2:
                    paymentProcessor = new PhonePeAdapter(new PhonePe());
                    break;
                case 3:
                    paymentProcessor = new RazorpayAdapter(new Razorpay());
                    break;
                default:
                    System.out.println("Invalid choice");
                    return;
            }

            paymentProcessor.processPayment(amount);
            scanner.close();
        }
    }
}

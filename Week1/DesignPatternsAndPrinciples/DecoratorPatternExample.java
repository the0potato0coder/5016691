package Week1.DesignPatternsAndPrinciples;

public class DecoratorPatternExample {

    interface Notifier {
        void send(String message);
    }

    static class EmailNotifier implements Notifier {
        @Override
        public void send(String message) {
            System.out.println("Sending Email: " + message);
        }
    }

    static abstract class NotifierDecorator implements Notifier {
        protected Notifier wrappedNotifier;

        public NotifierDecorator(Notifier notifier) {
            this.wrappedNotifier = notifier;
        }

        @Override
        public void send(String message) {
            wrappedNotifier.send(message);
        }
    }

    static class SMSNotifierDecorator extends NotifierDecorator {
        public SMSNotifierDecorator(Notifier notifier) {
            super(notifier);
        }

        @Override
        public void send(String message) {
            super.send(message);
            sendSMS(message);
        }

        private void sendSMS(String message) {
            System.out.println("Sending SMS: " + message);
        }
    }

    static class SlackNotifierDecorator extends NotifierDecorator {
        public SlackNotifierDecorator(Notifier notifier) {
            super(notifier);
        }

        @Override
        public void send(String message) {
            super.send(message);
            sendSlack(message);
        }

        private void sendSlack(String message) {
            System.out.println("Sending Slack message: " + message);
        }
    }

    public static class DecoratorPatternTest {
        public static void main(String[] args) {
            Notifier emailNotifier = new EmailNotifier();
            Notifier smsNotifier = new SMSNotifierDecorator(emailNotifier);
            Notifier slackNotifier = new SlackNotifierDecorator(smsNotifier);

            System.out.println("Sending notification via Email:");
            emailNotifier.send("Hello via Email!");

            System.out.println("\nSending notification via Email and SMS:");
            smsNotifier.send("Hello via Email and SMS!");

            System.out.println("\nSending notification via Email, SMS, and Slack:");
            slackNotifier.send("Hello via Email, SMS, and Slack!");
        }
    }
}

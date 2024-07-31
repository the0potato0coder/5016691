package Week1.DesignPatternsAndPrinciples;

public class SingletonPatternExample {

    public static class Logger {
        private static Logger instance;
        private Logger() {}
        public static Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }
        public void log(String message) {
            System.out.println("Log message: " + message);
        }
    }
    public static class SingletonTest {
        public static void main(String[] args) {
            Logger logger1 = Logger.getInstance();
            Logger logger2 = Logger.getInstance();
            logger1.log("First log message");
            logger2.log("Second log message");
            if (logger1 == logger2) {
                System.out.println("Both logger1 and logger2 are the same instance.");
            } else {
                System.out.println("logger1 and logger2 are different instances.");
            }
        }
    }
}

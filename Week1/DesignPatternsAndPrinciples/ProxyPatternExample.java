package Week1.DesignPatternsAndPrinciples;

public class ProxyPatternExample {

    interface Image {
        void display();
    }
    static class RealImage implements Image {
        private String fileName;

        public RealImage(String fileName) {
            this.fileName = fileName;
            loadImageFromServer();
        }

        private void loadImageFromServer() {
            System.out.println("Loading " + fileName + " from remote server...");
        }

        @Override
        public void display() {
            System.out.println("Displaying " + fileName);
        }
    }
    static class ProxyImage implements Image {
        private RealImage realImage;
        private String fileName;

        public ProxyImage(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(fileName);
            }
            realImage.display();
        }
    }
    public static class ProxyPatternTest {
        public static void main(String[] args) {
            Image image1 = new ProxyImage("photo1.jpg");
            Image image2 = new ProxyImage("photo2.jpg");
            image1.display();
            System.out.println("");
            image1.display();
            System.out.println("");
            image2.display();
            System.out.println("");
            image2.display();
        }
    }
}

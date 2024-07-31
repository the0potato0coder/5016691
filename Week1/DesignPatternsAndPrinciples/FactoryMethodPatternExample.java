package Week1.DesignPatternsAndPrinciples;

public class FactoryMethodPatternExample {

    // Step 2: Define Document Interface
    interface Document {
        void open();
    }

    // Step 3: Create Concrete Document Classes
    static class WordDocument implements Document {
        @Override
        public void open() {
            System.out.println("Opening a Word document.");
        }
    }

    static class PdfDocument implements Document {
        @Override
        public void open() {
            System.out.println("Opening a PDF document.");
        }
    }

    static class ExcelDocument implements Document {
        @Override
        public void open() {
            System.out.println("Opening an Excel document.");
        }
    }

    // Step 4: Implement the Factory Method
    static abstract class DocumentFactory {
        public abstract Document createDocument();
    }

    static class WordDocumentFactory extends DocumentFactory {
        @Override
        public Document createDocument() {
            return new WordDocument();
        }
    }

    static class PdfDocumentFactory extends DocumentFactory {
        @Override
        public Document createDocument() {
            return new PdfDocument();
        }
    }

    static class ExcelDocumentFactory extends DocumentFactory {
        @Override
        public Document createDocument() {
            return new ExcelDocument();
        }
    }

    // Step 5: Test the Factory Method Implementation
    public static class FactoryMethodTest {
        public static void main(String[] args) {
            DocumentFactory wordFactory = new WordDocumentFactory();
            Document wordDoc = wordFactory.createDocument();
            wordDoc.open();

            DocumentFactory pdfFactory = new PdfDocumentFactory();
            Document pdfDoc = pdfFactory.createDocument();
            pdfDoc.open();

            DocumentFactory excelFactory = new ExcelDocumentFactory();
            Document excelDoc = excelFactory.createDocument();
            excelDoc.open();
        }
    }
}

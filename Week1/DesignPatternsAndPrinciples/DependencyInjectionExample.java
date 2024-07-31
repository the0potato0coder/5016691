package Week1.DesignPatternsAndPrinciples;

public class DependencyInjectionExample {

    public interface CustomerRepository {
        Customer findCustomerById(String id);
    }

    public static class CustomerRepositoryImpl implements CustomerRepository {
        @Override
        public Customer findCustomerById(String id) {
            return new Customer(id, "John Doe");
        }
    }

    public static class Customer {
        private String id;
        private String name;

        public Customer(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    public static class CustomerService {
        private final CustomerRepository customerRepository;

        public CustomerService(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }

        public Customer getCustomerById(String id) {
            return customerRepository.findCustomerById(id);
        }
    }

    public static void main(String[] args) {
        
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        
        CustomerService customerService = new CustomerService(customerRepository);

        
        Customer customer = customerService.getCustomerById("12345");

        
        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Customer Name: " + customer.getName());
    }
}

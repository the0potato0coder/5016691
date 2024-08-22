package com.bookstore.repository;

import com.bookstore.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {
    private List<Customer> customers = new ArrayList<>();

    public List<Customer> findAll() {
        return customers;
    }

    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public void save(Customer customer) {
        customers.add(customer);
    }

    public void update(Long id, Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId().equals(id)) {
                customers.set(i, customer);
                break;
            }
        }
    }

    public boolean deleteById(Long id) {
        return customers.removeIf(c -> c.getId().equals(id));
    }
}

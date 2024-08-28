package com.bookstoreapi.service;

import com.bookstoreapi.dto.CustomerDTO;
import com.bookstoreapi.model.Customer;
import com.bookstoreapi.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Listing all Customers
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    // Getting Customer by Id
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(this::convertEntityToDTO).orElse(null);
    }

    // Creating a New Customer
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertDTOToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertEntityToDTO(savedCustomer);
    }

    // Updating an Existing Customer
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id)) {
            throw new ValidationException("Customer Not Found");
        }
        Customer customer = convertDTOToEntity(customerDTO);
        customer.setId(id);
        Customer updatedCustomer = customerRepository.save(customer);
        return convertEntityToDTO(updatedCustomer);
    }

    // Deleting a Customer
    public boolean deleteCustomer(Long id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    // Convert Entity to DTO
    private CustomerDTO convertEntityToDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    // Convert DTO to Entity
    private Customer convertDTOToEntity(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
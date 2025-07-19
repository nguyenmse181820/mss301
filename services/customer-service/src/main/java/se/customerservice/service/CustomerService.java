package se.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.customerservice.dto.request.CustomerRequest;
import se.customerservice.dto.request.ProfileUpdateRequest;
import se.customerservice.dto.response.CustomerResponse;
import se.customerservice.pojo.Customer;
import se.customerservice.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return convertToResponse(customer);
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists: " + request.getEmail());
        }

        Customer customer = Customer.builder()
                .customerName(request.getCustomerName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .customerBirthday(request.getCustomerBirthday())
                .customerStatus(request.getCustomerStatus())
                .role(request.getRole())
                .build();

        Customer savedCustomer = customerRepository.save(customer);
        return convertToResponse(savedCustomer);
    }

    public CustomerResponse updateCustomerByAdmin(Integer id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setCustomerName(request.getCustomerName());
        customer.setTelephone(request.getTelephone());
        customer.setCustomerBirthday(request.getCustomerBirthday());
        customer.setCustomerStatus(request.getCustomerStatus());
        customer.setRole(request.getRole());

        if (request.getEmail() != null && !customer.getEmail().equals(request.getEmail())) {
            if (customerRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists: " + request.getEmail());
            }
            customer.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Customer savedCustomer = customerRepository.save(customer);
        return convertToResponse(savedCustomer);
    }

    public void deleteCustomer(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    public CustomerResponse getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
        return convertToResponse(customer);
    }

    public CustomerResponse getProfileByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
        return convertToResponse(customer);
    }

    public CustomerResponse updateProfileByEmail(String email, ProfileUpdateRequest request) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));

        if (request.getCustomerName() != null) {
            customer.setCustomerName(request.getCustomerName());
        }
        if (request.getTelephone() != null) {
            customer.setTelephone(request.getTelephone());
        }
        if (request.getCustomerBirthday() != null) {
            customer.setCustomerBirthday(request.getCustomerBirthday());
        }
        if (request.getPassword() != null && !request.getPassword().trim().isEmpty()) {
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Customer savedCustomer = customerRepository.save(customer);
        return convertToResponse(savedCustomer);
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .customerName(customer.getCustomerName())
                .telephone(customer.getTelephone())
                .email(customer.getEmail())
                .customerBirthday(customer.getCustomerBirthday())
                .customerStatus(customer.getCustomerStatus())
                .build();
    }
}

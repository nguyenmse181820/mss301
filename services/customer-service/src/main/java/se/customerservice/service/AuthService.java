package se.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.customerservice.config.JwtTokenProvider;
import se.customerservice.dto.request.LoginRequest;
import se.customerservice.dto.request.RegisterRequest;
import se.customerservice.dto.response.LoginResponse;
import se.customerservice.exception.AccountDisabledException;
import se.customerservice.exception.BadRequestException;
import se.customerservice.exception.InvalidCredentialsException;
import se.customerservice.pojo.Customer;
import se.customerservice.pojo.enums.Role;
import se.customerservice.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public LoginResponse login(LoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new InvalidCredentialsException();
        }

        if (customer.getCustomerStatus() == false) {
            throw new AccountDisabledException();
        }

        String token = tokenProvider.generateToken(customer);        return LoginResponse.builder()
                .accessToken(token)
                .customerId(customer.getCustomerId())
                .role(customer.getRole())
                .build();
    }

    public void register(RegisterRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        Customer customer = Customer.builder()
                .customerName(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .customerBirthday(request.getBirthday())
                .customerStatus(true)
                .role(Role.CUSTOMER)
                .build();

        customerRepository.save(customer);
    }
}

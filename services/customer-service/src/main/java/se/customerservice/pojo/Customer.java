package se.customerservice.pojo;

import jakarta.persistence.*;
import lombok.*;
import se.customerservice.pojo.enums.Role;

import java.time.LocalDate;

@Entity
@Table(name = "Customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerID")
    private Integer customerId;

    @Column(name = "CustomerName", length = 50)
    private String customerName;

    @Column(name = "Telephone", length = 12)
    private String telephone;

    @Column(name = "Email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "CustomerBirthday")
    private LocalDate customerBirthday;

    @Column(name = "CustomerStatus")
    private Boolean customerStatus;
    
    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}


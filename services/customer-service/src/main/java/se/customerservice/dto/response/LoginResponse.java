package se.customerservice.dto.response;

import lombok.*;
import se.customerservice.pojo.enums.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private Integer customerId;
    private Role role;
}

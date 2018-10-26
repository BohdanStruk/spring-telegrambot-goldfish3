package goldfish.express.domain;

import goldfish.express.entity.enums.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String password;
    private UserRole role;

    public UserDTO(Long id, String firstName, String lastName, PasswordEncoder passwordEncoder) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = String.valueOf(id);
        this.password =  passwordEncoder.encode("123");
        this.role = (UserRole.ROLE_USER);

    }


}


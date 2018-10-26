package goldfish.express.entity;

import goldfish.express.entity.enums.UserRole;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
    private String firstName;
    private String lastName;
    private String address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

}

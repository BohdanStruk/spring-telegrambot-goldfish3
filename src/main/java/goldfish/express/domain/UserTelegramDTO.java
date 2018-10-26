package goldfish.express.domain;


import lombok.*;

@Data
@Getter
@Setter
public class UserTelegramDTO {
    private Long id;
    private String first_name;
    private String last_name;
    private String address;
    private String phone_number;
    private String password;

    public UserTelegramDTO(Long id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = String.valueOf(id);
        this.password = "123";
    }

}

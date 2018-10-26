package goldfish.express.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor



public class ChatTelegramEntity extends BaseEntity {
    public String first_name;
    public String last_name;
    public String username;
    public String type;
}

package goldfish.express.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatDTO {
    private Long id;
    private String first_name;
    private String last_name;
    private String username;
    private String type;

}

package goldfish.express.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FromDTO {
    private Long id;
    private Boolean isBot;
    private String first_name;
    private String last_name;
    private String username;
    private String languageCode;
}

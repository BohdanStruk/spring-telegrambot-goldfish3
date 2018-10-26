package goldfish.express.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateDTO {
    private MessageDTO message;
}

package goldfish.express.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExampleDTO {
    private Long updateId;
    private MessageDTO message;
}

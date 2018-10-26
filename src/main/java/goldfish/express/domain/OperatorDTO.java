package goldfish.express.domain;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OperatorDTO {
    private Long id;
    private String first_name;
    private String last_name;
}

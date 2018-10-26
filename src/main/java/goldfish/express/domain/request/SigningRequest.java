package goldfish.express.domain.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SigningRequest {
    private String username;
    private String password;
}

package goldfish.express.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    private String message;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;


    public ErrorMessage(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}

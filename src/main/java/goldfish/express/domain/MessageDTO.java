package goldfish.express.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {
    private Long message_id;
    private FromDTO from;
    private ChatDTO chat;
    private Long date;
    private String text;

    public boolean hasText(){
        return (text != null && !text.isEmpty());
    }
}

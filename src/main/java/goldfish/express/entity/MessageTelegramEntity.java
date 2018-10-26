package goldfish.express.entity;

public class MessageTelegramEntity extends BaseEntity {
    public FromTelegramEntity from;
    public ChatTelegramEntity chat;
    public Long date;
    public String text;
}

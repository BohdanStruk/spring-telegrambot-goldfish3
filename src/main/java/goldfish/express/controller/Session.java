package goldfish.express.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class Session {
    private long id;
    private String address="РўСѓС‚ С‰Рµ РЅС–С‡РѕРіРѕ РЅРµРјР°.";
    private boolean addingAddress;
    private boolean loggingIn;
    private boolean operator;
    private boolean isInChat;
    private long interviewerId;
    private boolean addingPhoneNumber;
    private String phoneNumber;

    Session(long id) {
        this.id = id;
    }



    public void stopChat() {
        this.isInChat = false;
        this.interviewerId = 0;}
    public void startChat(long id) {
        this.isInChat = true;
        this.interviewerId= id;
    }
}

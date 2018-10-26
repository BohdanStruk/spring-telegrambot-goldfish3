package goldfish.express.controller;

import com.liqpay.LiqPay;
import goldfish.express.MessageSender;
import goldfish.express.domain.*;
import goldfish.express.service.OperatorService;
import goldfish.express.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/")
public class BotController {
    private static final String PUBLIC_KEY = "i4651215010";
    private static final String PRIVATE_KEY = "OAbE9WviQjqe34pHmF9ws29MNzEO7i91gzzlkyuS";
    private  HashMap<Long, Session> activeUsers;
    private  List<Long> waitingUsers;

    @Autowired
    public  OperatorService operatorService;
    @Autowired
    public UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/", method = GET)
    public String index() {
        return "index";
    }
    @GetMapping("telegram")
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> userDTOS = userService.getAll();
        return new ResponseEntity<List<UserDTO>>(userDTOS, HttpStatus.OK);
    }


    @PostMapping("add")
    public ResponseEntity<Void> createUser(@RequestBody UserDTO dto){
        userService.createUser(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Void> getUpdate(@RequestBody UpdateDTO updateDTO){
        firstSet();
        System.out.println(updateDTO.toString());
        onUpdateReceived(updateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    boolean set=false;

    private void firstSet() {
        if(!set){
            activeUsers = new HashMap<>();
            waitingUsers = new ArrayList<>();
            if(operatorService.findAllOperators()!=null)for (long l : operatorService.findAllOperators()) {
                Session s = new Session(l);
                s.setOperator(true);
                activeUsers.put(l, s);
            }
            set = true;
        }
    }

    private void onUpdateReceived(UpdateDTO update) {
        Session s;
        MessageDTO message = update.getMessage();
        long id = message.getChat().getId();
        if (activeUsers.containsKey(id)) {
            s = activeUsers.get(id);
        } else {
            s = new Session(id);
            activeUsers.put(id, s);
        }
        if(!userService.isUserPresent(id)) {
            userService.createUser(new UserDTO(id, message.getChat().getFirst_name(), message.getChat().getLast_name(), passwordEncoder));
        }
        if (message.hasText()) {
            if (!s.isOperator()) {
                switch (message.getText()) {
                    case "/help":
                        sendMag(message, "Привіт!");
                        break;
                    case "/start":
                        sendMag(message, "Привіт! Я зможу зробити усе, що тобі забажається. У рамках закону, звісно =). Тут ти також зможеш зберігти свою адресу, щоб не писати знову для наступних звернень, а також оплачувати замовлення одним кліком.");
                        break;
                    case "/start_magic":
                        if(!s.isInChat()) notifyAllOperators(id);
                        break;
                    case "/address":
                        sendMag(message, "Напиши свою адресу у форматі: \"Місто, Вулиця, Квартира\" У наступному повідомленні.");
                        s.setAddingAddress(true);
                        break;
                    case "/phone_number":
                        sendMag(message, "Напиши свій номер телефону у форматі \"'+380 '\" У наступному повідомленні.");
                        s.setAddingPhoneNumber(true);
                        break;
                    case "/my_address":
                        try {
                            s.setAddress(userService.getAddress(id));
                        } catch (Exception e) {
                            s.setAddress("Будь ласка, збережіть адресу використовуючи команду /address");
                        }
                        sendMag(message, s.getAddress());
                        break;
                    case "/my_phone_number":
                        try {
                            s.setPhoneNumber(userService.getPhoneNumber(id));
                        }catch (Exception e) {
                            s.setPhoneNumber("Будь ласка, збережіть номер телефону використовуючи команду /phoneNumber");
                        }
                        sendMag(message, s.getPhoneNumber());
                        break;
                    case "/pay":
                        sendMag(message,"https://www.liqpay.ua/api/3/checkout?data=eyJ2ZXJzaW9uIjozLCJhY3Rpb24iOiJwYXkiLCJwdWJsaWNfa2V5IjoiaTQ2NTEyMTUwMTAiLCJhbW91bnQiOiI1IiwiY3VycmVuY3kiOiJVQUgiLCJkZXNjcmlwdGlvbiI6ItCc0L7QuSDRgtC%2B0LLQsNGAIiwidHlwZSI6ImJ1eSIsImxhbmd1YWdlIjoicnUifQ%3D%3D&signature=8xxD0LxWHuDeJLj4%2BniByLqshCU%3D");
                        break;
                    case "/login":
                        sendMag(message, "Я вас зрозумів але поки нічого з цим не зможу можу зробити =)");
                        s.setLoggingIn(true);
                        break;
                    default:
                        if (s.isAddingAddress()) {
                            s.setAddress(message.getText());
                            userService.saveAddress(s.getAddress(), id);

                            s.setAddingAddress(false);
                            sendMag(message, "Дякую! Запам'ятав");

                        }else if (s.isAddingPhoneNumber()){
                            s.setPhoneNumber(message.getText());
                            userService.savePhoneNumber(s.getPhoneNumber(), id);

                            s.setAddingPhoneNumber(false);
                            sendMag(message, "Дякую! Запам'ятав");
                        }else if (s.isInChat()) {
                            sendMagTo(message.getText(), s.getInterviewerId());
                        }   else if (s.isLoggingIn() && message.getText().equals("1234")) {
                            operatorService.saveOperator(new OperatorDTO(id, message.getChat().getFirst_name(), message.getChat().getLast_name()));
                            s.setOperator(true);
                            sendMag(message, "Успішно залогінено як оператора.");

                        } else {
                            sendMag(message, "Я вас зрозумів але поки нічого з цим не зможу можу зробити =)");
                            System.out.println(message.getText());
                        }
                        break;
                }
            } else if (s.isOperator()){
                long currentUserId = 0;
                if (waitingUsers.size() != 0) {
                    currentUserId = waitingUsers.get(0);
                }

                long operId = s.getId();

                switch (message.getText()) {
                    case "/go":
                        sendMagTo("Чат почато", operId);
                        sendMagTo("Чат почато", currentUserId);

                        if (currentUserId != 0) {
                            activeUsers.get(currentUserId).startChat(id);
                            s.startChat(currentUserId);

                            waitingUsers.remove(0);

                        }
                        break;
                    case "/stop":
                        if (s.getInterviewerId() != 0) {
                            sendMagTo("Розмову завершено", operId);
                            sendMagTo("Розмову завершено", s.getInterviewerId());
                            activeUsers.get(s.getInterviewerId()).stopChat();
                            s.stopChat();


                        }
                        break;
                    default:
                        if (s.isInChat()) sendMagTo(message.getText(), s.getInterviewerId());
                        break;
                }
            }
        }
    }


    private  void notifyAllOperators(long userId) {
        if (!waitingUsers.contains(userId)) waitingUsers.add(userId);
        for (long id : activeUsers.keySet()) {
            if (activeUsers.get(id).isOperator())
                sendMagTo("Прийняти замовлення. Наразі у черзі " + waitingUsers.size() + " клієнтів.", id);
        }
    }

    private  void sendMag(MessageDTO message, String text) {
        MessageSender sendMessage = new MessageSender();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChat().getId().toString());
        sendMessage.setReplyToMessageId(message.getMessage_id());
        sendMessage.setText(text);
        sendMessage.execute();
    }


    private  void sendMagTo(String text, long id) {
        MessageSender sendMessage = new MessageSender();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(String.valueOf(id));
        sendMessage.setText(text);
        sendMessage.execute();

    }

}

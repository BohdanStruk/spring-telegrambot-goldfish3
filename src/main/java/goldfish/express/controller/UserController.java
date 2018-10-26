package goldfish.express.controller;

import goldfish.express.domain.UserDTO;
import goldfish.express.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDTO dto) {
        userService.save(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = userService.findAll();
        return new ResponseEntity<List<UserDTO>>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("{phoneNumber}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("phoneNumber") String phoneNumber) {
            UserDTO userDTO = userService.findByPhoneNumber(phoneNumber);
            return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

}

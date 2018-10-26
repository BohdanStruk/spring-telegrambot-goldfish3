package goldfish.express.controller;

import goldfish.express.domain.UserDTO;
import goldfish.express.domain.request.SigningRequest;
import goldfish.express.domain.response.SigninResponse;
import goldfish.express.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO dto){
        System.out.println("Call ");
        userService.save(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigningRequest request){
        String token = userService.signin(request.getUsername(), request.getPassword());
        String role = "";
        if(token != null){
            role = userService.findByPhoneNumber(request.getUsername()).getRole().toString();
        }
        return new ResponseEntity<SigninResponse>(new SigninResponse(token, role), HttpStatus.OK);
    }
}
